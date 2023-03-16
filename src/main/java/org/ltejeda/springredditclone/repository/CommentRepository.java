package org.ltejeda.springredditclone.repository;

import org.ltejeda.springredditclone.model.Comment;
import org.ltejeda.springredditclone.model.Post;
import org.ltejeda.springredditclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);
    List<Comment> findAllByUser(User user);
}
