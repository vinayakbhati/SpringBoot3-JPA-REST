package com.survey.restsurvey.user;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserDetailsPagingSorting extends PagingAndSortingRepository<UserDetails, Long> {

}
