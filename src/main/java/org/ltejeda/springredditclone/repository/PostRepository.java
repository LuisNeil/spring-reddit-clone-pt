package org.ltejeda.springredditclone.repository;

import org.ltejeda.springredditclone.model.Post;
import org.ltejeda.springredditclone.model.Subreddit;
import org.ltejeda.springredditclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllBySubreddit(Subreddit subreddit);
    List<Post> findByUser(User user);
}
