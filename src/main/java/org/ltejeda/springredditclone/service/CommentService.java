package org.ltejeda.springredditclone.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ltejeda.springredditclone.dto.CommentsDto;
import org.ltejeda.springredditclone.exceptions.PostNotFoundException;
import org.ltejeda.springredditclone.mapper.CommentMapper;
import org.ltejeda.springredditclone.model.Comment;
import org.ltejeda.springredditclone.model.Post;
import org.ltejeda.springredditclone.model.User;
import org.ltejeda.springredditclone.repository.CommentRepository;
import org.ltejeda.springredditclone.repository.PostRepository;
import org.ltejeda.springredditclone.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class CommentService {
    private final CommentMapper commentMapper;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final MailService mailService;
    private final MailContentBuilder mailContentBuilder;

    public void createComment(CommentsDto commentsDto){
        Post post = postRepository.findById(commentsDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException(commentsDto.getPostId().toString()));
        Comment comment = commentMapper.map(commentsDto, post, authService.getCurrentUser());
        commentRepository.save(comment);
    }

    public List<CommentsDto> getCommentsByPost(Long postId){
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));
        return commentRepository.findByPost(post).stream().map(commentMapper::mapToDto).collect(Collectors.toList());
    }

    public List<CommentsDto> getCommentsByUser(String userName){
        User user = userRepository.findByUsername(userName).orElseThrow(() -> new UsernameNotFoundException(userName));
        return commentRepository.findAllByUser(user).stream().map(commentMapper::mapToDto).collect(Collectors.toList());
    }

}
