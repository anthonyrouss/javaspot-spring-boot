package gr.unipi.javaspot.dtos;

@FunctionalInterface
public interface DtoConvertible<T> {
    T toDto();
}
