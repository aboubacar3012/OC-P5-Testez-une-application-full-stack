package com.openclassrooms.starterjwt.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class EntityMapperTest {

    @Test
    void testToEntity() {
        // Given
        EntityMapper<DummyDto, DummyEntity> mapper = new DummyMapper();
        DummyDto dto = new DummyDto("test");

        // When
        DummyEntity entity = mapper.toEntity(dto);

        // Then
        assertThat(entity).isNotNull();
        assertThat(entity.getName()).isEqualTo(dto.getName());
    }

    @Test
    void testToDto() {
        // Given
        EntityMapper<DummyDto, DummyEntity> mapper = new DummyMapper();
        DummyEntity entity = new DummyEntity("test");

        // When
        DummyDto dto = mapper.toDto(entity);

        // Then
        assertThat(dto).isNotNull();
        assertThat(dto.getName()).isEqualTo(entity.getName());
    }

    @Test
    void testToEntityList() {
        // Given
        EntityMapper<DummyDto, DummyEntity> mapper = new DummyMapper();
        List<DummyDto> dtoList = Arrays.asList(new DummyDto("test1"), new DummyDto("test2"));

        // When
        List<DummyEntity> entityList = mapper.toEntity(dtoList);

        // Then
        assertThat(entityList).hasSize(2);
        assertThat(entityList.get(0).getName()).isEqualTo(dtoList.get(0).getName());
        assertThat(entityList.get(1).getName()).isEqualTo(dtoList.get(1).getName());
    }

    @Test
    void testToDtoList() {
        // Given
        EntityMapper<DummyDto, DummyEntity> mapper = new DummyMapper();
        List<DummyEntity> entityList = Arrays.asList(new DummyEntity("test1"), new DummyEntity("test2"));

        // When
        List<DummyDto> dtoList = mapper.toDto(entityList);

        // Then
        assertThat(dtoList).hasSize(2);
        assertThat(dtoList.get(0).getName()).isEqualTo(entityList.get(0).getName());
        assertThat(dtoList.get(1).getName()).isEqualTo(entityList.get(1).getName());
    }

    private static class DummyDto {
        private String name;

        public DummyDto(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private static class DummyEntity {
        private String name;

        public DummyEntity(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private static class DummyMapper implements EntityMapper<DummyDto, DummyEntity> {

        @Override
        public DummyEntity toEntity(DummyDto dto) {
            return new DummyEntity(dto.getName());
        }

        @Override
        public DummyDto toDto(DummyEntity entity) {
            return new DummyDto(entity.getName());
        }

        @Override
        public List<DummyEntity> toEntity(List<DummyDto> dtoList) {
            return dtoList.stream().map(this::toEntity).toList();
        }

        @Override
        public List<DummyDto> toDto(List<DummyEntity> entityList) {
            return entityList.stream().map(this::toDto).toList();
        }

    }

}
