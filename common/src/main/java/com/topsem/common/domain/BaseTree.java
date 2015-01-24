package com.topsem.common.domain;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.Lists;
import com.topsem.common.domain.view.View;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 基础树父类
 *
 * @author Chen on 14-12-11.
 */
@Data
@ToString(of = {"name"})
@MappedSuperclass
@EqualsAndHashCode(callSuper = true)
public class BaseTree<T extends BaseTree> extends NamedEntity implements Sortable {

    @JsonView(View.Public.class)
    private int SN; // 对应的JavaScript类

    @ManyToOne
    @JoinColumn(name = "parentId")
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonView(View.WithParent.class)
    @JsonSerialize(using = CustomTreeSerialize.class)
    private T parent;  //父节点

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    @OrderBy(value = "SN")
    @NotFound(action = NotFoundAction.IGNORE)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonView(View.WithChildren.class)
    private List<T> children = Lists.newArrayList(); // 子节点

    public void addChildren(T tree) {
        this.children.add(tree);
    }

    public static class CustomTreeSerialize extends JsonSerializer<NamedEntity> {
        @Override
        public void serialize(NamedEntity entity, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", entity.getId());
            map.put("name", entity.getName());
            mapper.writeValue(jsonGenerator, map);
        }
    }
}

