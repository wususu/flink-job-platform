package mapper;

import model.TableConf;

import java.util.List;

public interface TableConfMapper {

    List<TableConf> getAll();

    TableConf getOne(Integer tid);

    void insert(TableConf user);

    void update(TableConf user);

    void delete(Integer tid);

}
