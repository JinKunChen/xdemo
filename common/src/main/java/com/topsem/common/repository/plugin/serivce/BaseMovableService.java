/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.topsem.common.repository.plugin.serivce;

import com.topsem.common.domain.BaseEntity;
import com.topsem.common.repository.jpa.support.Query;
import com.topsem.common.repository.plugin.entity.Movable;
import com.topsem.common.service.BaseService;
import org.springframework.aop.framework.AopContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.List;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-2-22 下午2:34
 * <p>Version: 1.0
 */
public abstract class BaseMovableService<M extends BaseEntity & Movable, ID extends Serializable> extends BaseService<M, ID> {


    //权重的步长
    private final Integer stepLength;

    /**
     * 默认步长1000
     */
    protected BaseMovableService() {
        this(1000);

    }

    protected BaseMovableService(Integer stepLength) {
        this.stepLength = stepLength;
    }

    /**
     * 权重的步长 默认1000
     *
     * @return
     */
    public Integer getStepLength() {
        return stepLength;
    }

    @Override
    public M save(M m) {
        if (m.getWeight() == null) {
            m.setWeight(findNextWeight());
        }
        return super.save(m);
    }

    /**
     * 按照降序进行移动
     * 把{fromId}移动到{}toId}之后
     * 如 fromWeight 2000 toWeight 1000   则新的为 500
     *
     * @param fromId
     * @param toId
     */
    public void down(ID fromId, ID toId) {
        M from = findOne(fromId);
        M to = findOne(toId);
        if (from == null || to == null || from.equals(to)) {
            return;
        }
        Integer fromWeight = from.getWeight();
        Integer toWeight = to.getWeight();


        M nextTo = findNextByWeight(toWeight);

        //如果toId的下一个是fromId 则直接交换顺序即可
        if (from.equals(nextTo)) {
            from.setWeight(toWeight);
            to.setWeight(fromWeight);
            return;
        }

        int minWeight = Math.min(fromWeight, toWeight);
        int maxWeight = Math.max(fromWeight, toWeight);
        //作为一个优化，减少不连续的weight
        int count = countByBetween(minWeight, maxWeight).intValue();
        if (count > 0 && count < 20) {
            List<M> moves = findByBetweenAndAsc(minWeight, maxWeight);
            if (fromWeight < toWeight) {
                Integer swapInteger = moves.get(count - 2).getWeight();
                for (int i = count - 2; i >= 1; i--) {
                    //最后一个的weight = toWeight;
                    moves.get(i).setWeight(moves.get(i - 1).getWeight());
                }
                moves.get(0).setWeight(swapInteger);
            } else {
                for (int i = 0; i <= count - 2; i++) {
                    moves.get(i).setWeight(moves.get(i + 1).getWeight());
                }
                moves.get(count - 1).setWeight(minWeight);
            }
            return;
        }

        M preTo = findPreByWeight(toWeight);

        //计算新的权重
        int newWeight = 0;
        if (preTo == null) {
            newWeight = toWeight / 2;
        } else {
            newWeight = toWeight - (toWeight - preTo.getWeight()) / 2;

        }

        if (Math.abs(newWeight - toWeight) <= 1) {
            throw new IllegalStateException(String.format("up error, no enough weight :fromId:%d, toId:%d", fromId, toId));
        }
        from.setWeight(newWeight);

    }

    /**
     * 按照降序进行移动
     * 把{fromId}移动到toId之下
     * 如 fromWeight 1000 toWeight 2000  3000 则新的为 2500
     *
     * @param fromId
     * @param toId
     */
    public void up(ID fromId, ID toId) {
        M from = findOne(fromId);
        M to = findOne(toId);
        if (from == null || to == null || from.equals(to)) {
            return;
        }
        Integer fromWeight = from.getWeight();
        Integer toWeight = to.getWeight();


        M preTo = findPreByWeight(toWeight);
        //如果toId的下一个是fromId 则直接交换顺序即可
        if (from.equals(preTo)) {
            from.setWeight(toWeight);
            to.setWeight(fromWeight);
            return;
        }

        int minWeight = Math.min(fromWeight, toWeight);
        int maxWeight = Math.max(fromWeight, toWeight);
        //作为一个优化，减少不连续的weight
        int count = countByBetween(minWeight, maxWeight).intValue();
        if (count > 0 && count < 20) {
            List<M> moves = findByBetweenAndDesc(minWeight, maxWeight);
            //123/124
            //5000 4000 3000

            if (fromWeight > toWeight) {
                Integer swapInteger = moves.get(count - 2).getWeight();
                for (int i = count - 2; i >= 1; i--) {
                    //最后一个的weight = toWeight;
                    moves.get(i).setWeight(moves.get(i - 1).getWeight());
                }
                moves.get(0).setWeight(swapInteger);
            } else {
                for (int i = 0; i <= count - 2; i++) {
                    moves.get(i).setWeight(moves.get(i + 1).getWeight());
                }
                moves.get(count - 1).setWeight(maxWeight);
            }
            return;
        }

        //如果toId的下一个是fromId 则直接交换顺序即可
        if (from.equals(preTo)) {
            from.setWeight(toWeight);
            to.setWeight(fromWeight);
            return;
        }
        M nextTo = findNextByWeight(toWeight);

        //计算新的权重
        int newWeight = 0;
        if (nextTo == null) {
            newWeight = toWeight + stepLength;
        } else {
            newWeight = toWeight + (nextTo.getWeight() - toWeight) / 2;
        }

        if (Math.abs(newWeight - toWeight) <= 1) {
            throw new IllegalStateException(String.format("down error, no enough weight :fromId:%d, toId:%d", fromId, toId));
        }
        from.setWeight(newWeight);
    }

    public void reweight() {
        int batchSize = 100;
        Sort sort = new Sort(Sort.Direction.DESC, "weight");
        Pageable pageable = new PageRequest(0, batchSize, sort);
        Page<M> page = findAll(pageable);
        do {
            //doReweight需要requiresNew事务
            ((BaseMovableService) AopContext.currentProxy()).doReweight(page);

            if (page.isLast()) {
                break;
            }

            pageable = new PageRequest((pageable.getPageNumber() + 1) * batchSize, batchSize, sort);

            page = findAll(pageable);

        } while (true);
    }

    public void doReweight(Page<M> page) {
        int totalElements = (int) page.getTotalElements();
        List<M> moves = page.getContent();

        int firstElement = page.getNumber() * page.getSize();

        for (int i = 0; i < moves.size(); i++) {
            M move = moves.get(i);
            move.setWeight((totalElements - firstElement - i) * stepLength);
            update(move);
        }

    }

    private Integer findNextWeight() {
        Page<M> page = findAll(new PageRequest(0, 1, new Sort(Sort.Direction.DESC, "weight")));
        if (!page.hasContent()) {
            return stepLength;
        }
        return page.getContent().get(0).getWeight() + stepLength;
    }

    public M findPreByWeight(Integer weight) {
        Page<M> page = findWithPage(Query.<M>where("weight_lt", weight).desc("weight").paging(0, 1));
        if (page.hasContent()) {
            return page.getContent().get(0);
        }
        return null;
    }

    public M findNextByWeight(Integer weight) {
        Page<M> page = findWithPage(Query.<M>where("weight_gt", weight).asc("weight").paging(0, 1));
        if (page.hasContent()) {
            return page.getContent().get(0);
        }
        return null;
    }

    private Long countByBetween(Integer minWeight, Integer maxWeight) {
        return count(Query.<M>where("weight_gte", minWeight).and("weight_lte", maxWeight));
    }

    //@Query(value = "from Move m where m.weight>=?1 and m.weight <= ?2 order by m.weight asc")
    List<M> findByBetweenAndAsc(Integer minWeight, Integer maxWeight) {
        return findAllWithSort(Query.<M>where("weight_gte", minWeight).and("weight_lte", maxWeight).asc("weight"));
    }

    List<M> findByBetweenAndDesc(Integer minWeight, Integer maxWeight) {
        return findAllWithSort(Query.<M>where("weight_gte", minWeight).and("weight_lte", maxWeight).desc("weight"));
    }

}
