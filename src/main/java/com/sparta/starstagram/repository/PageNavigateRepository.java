package com.sparta.starstagram.repository;

import com.sparta.starstagram.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PageNavigateRepository extends JpaRepository <Board,Long> { }
