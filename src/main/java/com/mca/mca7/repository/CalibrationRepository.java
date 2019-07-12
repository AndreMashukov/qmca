package com.mca.mca7.repository;

import com.mca.mca7.domain.Calibration;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Calibration entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CalibrationRepository extends JpaRepository<Calibration, Long> {

}
