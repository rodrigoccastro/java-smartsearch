import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.Tuple;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Scope("singleton")
public class SmartSearchxService {

    private Map<String,List<TreexDTO>> hashTypes = new HashMap<>();

    public void init(String type, List<Tuple> list) {
        hashTypes.put(type,getListTree(list));
    }

    public List<TreexDTO> find(String type, String query, int maxResult) {
        List<TreexDTO> list = hashTypes.get(type);
        Predicate<TreexDTO> predicate = (TreexDTO element) -> (element.getName()!=null && element.getName().toUpperCase().contains(query.toUpperCase()));
        return list.stream().filter(predicate).limit(maxResult).collect(Collectors.toList());
    }

    private List<TreexDTO> getListTree(List<Tuple> list) {
        return list.stream().map(n -> getObjFromTupleToTree(n)).collect(Collectors.toList());
    }

    private TreexDTO getObjFromTupleToTree(Tuple tuple) {
        TreexDTO item = new TreexDTO();
        item.setId((Integer) tuple.get("id"));
        item.setName(tuple.get("name").toString());
        return item;
    }

}
