package ru.probe.repos;

import org.springframework.data.repository.CrudRepository;
import ru.probe.dbmodels.Status;

public interface StatusRepository extends CrudRepository<Status, String> {

}
