package com.eurowings.newsletter.repository;

import com.eurowings.newsletter.model.Subscription;
import com.eurowings.newsletter.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription,Long>{

    List<Subscription> findByUser(User user);

    Optional<Subscription> findTopByUserAndCreationDateBetweenOrderByCreationDateDesc(User user,Date start, Date stop);

    Optional<Subscription> findTopByUserOrderByCreationDateDesc(User user);

    @Query("SELECT DISTINCT s.user FROM Subscription s where s.creationDate BETWEEN :start and :end")
    List findDistinctUsersByCreationDate(@Param("start") Date startDate, @Param("end") Date endDate);
}
