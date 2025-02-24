package net.engineeringdigest.journalApp.repository;

import net.engineeringdigest.journalApp.entity.SchoolData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolDataRepository extends JpaRepository<SchoolData,Integer> {
}
