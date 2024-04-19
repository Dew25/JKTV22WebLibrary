package ee.ivkhk.JKTV22WebLibrary.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class Cover {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileName;
    private String pathToCover;

    public Cover() {
    }

    public Cover(String fileName, String pathToCover) {
        this.fileName = fileName;
        this.pathToCover = pathToCover;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPathToCover() {
        return pathToCover;
    }

    public void setPathToCover(String pathToCover) {
        this.pathToCover = pathToCover;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cover cover = (Cover) o;
        return Objects.equals(id, cover.id) && Objects.equals(fileName, cover.fileName) && Objects.equals(pathToCover, cover.pathToCover);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fileName, pathToCover);
    }

    @Override
    public String toString() {
        return "Cover{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", pathToCover='" + pathToCover + '\'' +
                '}';
    }
}
