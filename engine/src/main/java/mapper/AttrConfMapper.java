package mapper;

import model.AttrConf;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttrConfMapper {
    List<AttrConf> getAll();

    AttrConf getOne(Integer aid);

    void insert(AttrConf user);

    void update(AttrConf user);

    void delete(Integer id);
}
