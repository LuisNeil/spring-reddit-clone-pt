package org.ltejeda.springredditclone.repository;

import org.ltejeda.springredditclone.model.Post;
import org.ltejeda.springredditclone.model.Subreddit;
import org.ltejeda.springredditclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllBySubreddit(Subreddit subreddit);
    List<Post> findByUser(User user);
}
