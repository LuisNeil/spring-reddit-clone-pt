package org.ltejeda.springredditclone.repository;

import org.ltejeda.springredditclone.model.Post;
import org.ltejeda.springredditclone.model.User;
import org.ltejeda.springredditclone.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentuser);
}
