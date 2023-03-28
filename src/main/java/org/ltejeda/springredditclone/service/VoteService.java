package org.ltejeda.springredditclone.service;

import lombok.RequiredArgsConstructor;
import org.ltejeda.springredditclone.dto.VoteDto;
import org.ltejeda.springredditclone.exceptions.PostNotFoundException;
import org.ltejeda.springredditclone.exceptions.SpringRedditException;
import org.ltejeda.springredditclone.model.Post;
import org.ltejeda.springredditclone.model.Vote;
import org.ltejeda.springredditclone.model.VoteType;
import org.ltejeda.springredditclone.repository.PostRepository;
import org.ltejeda.springredditclone.repository.VoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.ltejeda.springredditclone.model.VoteType.UPVOTE;

@Service
@RequiredArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final AuthService authService;

    @Transactional
    public void vote(VoteDto voteDto){
        Post post = postRepository.findById(voteDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException("Post Not Found with Id - " + voteDto.getPostId()));
        Optional<Vote> voteByPostAndUser = voteRepository
                .findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());
        if(voteByPostAndUser.isPresent() && voteByPostAndUser.get().getVoteType().equals(voteDto.getVoteType())){
            throw new SpringRedditException("You have already " + voteDto.getVoteType()+ "'d for this post");
        }
        if(UPVOTE.equals(voteDto.getVoteType())){
            post.setVoteCount(post.getVoteCount() + 1);
        }  else {
            post.setVoteCount(post.getVoteCount() - 1);
        }
        voteRepository.save(mapToVote(voteDto, post));
        postRepository.save(post);
    }

    private Vote mapToVote(VoteDto voteDto, Post post) {
        return Vote.builder().voteType(voteDto.getVoteType()).post(post).user(authService.getCurrentUser()).build();
    }
}
