package or.korea.teami.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import or.korea.teami.entity.Post;


public interface PostRepository extends JpaRepository<Post,Long> {

}
