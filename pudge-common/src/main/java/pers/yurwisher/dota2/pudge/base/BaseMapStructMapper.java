package pers.yurwisher.dota2.pudge.base;

import java.util.List;

/**
 * mapStruct 基类, D,E互相转化,基于set get方法
 * @param <D> dto
 * @param <E> entity
 * @author yq
 */
public interface BaseMapStructMapper<D, E> {

    /**
     * DTO转Entity
     * @param dto /
     * @return /
     */
    E toEntity(D dto);

    /**
     * Entity转DTO
     * @param entity /
     * @return /
     */
    D toDto(E entity);

    /**
     * DTO集合转Entity集合
     * @param dtoList /
     * @return /
     */
    List <E> toEntity(List<D> dtoList);

    /**
     * Entity集合转DTO集合
     * @param entityList /
     * @return /
     */
    List <D> toDto(List<E> entityList);
}
