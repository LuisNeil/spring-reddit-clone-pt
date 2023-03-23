package org.ltejeda.springredditclone.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ltejeda.springredditclone.dto.PostRequest;
import org.ltejeda.springredditclone.dto.PostResponse;
import org.ltejeda.springredditclone.exceptions.PostNotFoundException;
import org.ltejeda.springredditclone.exceptions.SubredditNotFoundException;
import org.ltejeda.springredditclone.mapper.PostMapper;
import org.ltejeda.springredditclone.model.Post;
import org.ltejeda.springredditclone.model.Subreddit;
import org.ltejeda.springredditclone.model.User;
import org.ltejeda.springredditclone.repository.PostRepository;
import org.ltejeda.springredditclone.repository.SubredditRepository;
import org.ltejeda.springredditclone.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final SubredditRepository subredditRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final PostMapper postMapper;

    public void save(PostRequest postRequest) {
        Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName())
                .orElseThrow(() -> new SubredditNotFoundException(postRequest.getSubredditName()));
        postRepository.save(postMapper.map(postRequest,subreddit,authService.getCurrentUser()));

    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPost() {
        return postRepository.findAll().stream().map(postMapper::mapToDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id.toString()));
        return postMapper.mapToDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsBySubreddit(Long subredditId) {
        Subreddit subreddit = subredditRepository
                .findById(subredditId).orElseThrow(() -> new SubredditNotFoundException(subredditId.toString()));
        List<Post> allBySubreddit = postRepository.findAllBySubreddit(subreddit);
        return allBySubreddit.stream().map(postMapper::mapToDto).collect(Collectors.toList());
    }

    public List<PostResponse> getPostsByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return postRepository.findByUser(user).stream().map(postMapper::mapToDto).collect(Collectors.toList());
    }
}
