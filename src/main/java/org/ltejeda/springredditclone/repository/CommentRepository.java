package org.ltejeda.springredditclone.repository;

import org.ltejeda.springredditclone.model.Comment;
import org.ltejeda.springredditclone.model.Post;
import org.ltejeda.springredditclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);
    List<Comment> findAllByUser(User user);
}
