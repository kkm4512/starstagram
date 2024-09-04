package com.sparta.starstagram.repository;

import com.sparta.starstagram.entity.Post;
import com.sparta.starstagram.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PageNavigateRepository extends JpaRepository <Post,Long> {
    /**
     * Post 테이블의 user가 IN 조건에 맞는지 검사한다
     * Friend테이블에서의 user와, 매개변수로 받은 user가 동일한지를 검사하고
     * 동일한 Post들을, 작성일 기준으로 내림차순하여 Page 객체로 반환
     *
     * @param user 로그인한 유저
     * @param pageable 페이저블 객체
     * @return 로그인한 사용자의 친구들의 게시글들 반환
     */
    @Query("SELECT p FROM Post p WHERE p.user IN (SELECT f.friend FROM Friend f WHERE f.user = :user) ORDER BY p.createdAt DESC")
    Page<Post> findPostsByUserFriends(@Param("user") User user, Pageable pageable);

    /**
     *
     * @param user 로그인한 유저
     * @param pageable 페이저블 객체
     * @return 로그인한 사용자의 친구들의 게시글들 + 자신의 게시글 반환
     */
    @Query("SELECT p FROM Post p WHERE p.user = :user OR p.user IN (SELECT f.friend FROM Friend f WHERE f.user = :user) ORDER BY p.createdAt DESC")
    Page<Post> findPostsByUserAndUserFriends(@Param("user") User user, Pageable pageable);


}
