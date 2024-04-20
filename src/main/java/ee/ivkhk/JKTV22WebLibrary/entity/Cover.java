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
    private String pathToFullCover;
    private String pathToMinCover;


    public Cover() {
    }

    public Cover(String fileName, String pathToFullCover, String pathToMinCover) {
        this.fileName = fileName;
        this.pathToFullCover = pathToFullCover;
        this.pathToMinCover = pathToMinCover;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPathToFullCover() {
        return pathToFullCover;
    }

    public void setPathToFullCover(String pathToFullCover) {
        this.pathToFullCover = pathToFullCover;
    }

    public String getPathToMinCover() {
        return pathToMinCover;
    }

    public void setPathToMinCover(String pathToMinCover) {
        this.pathToMinCover = pathToMinCover;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cover cover = (Cover) o;
        return Objects.equals(id, cover.id) && Objects.equals(fileName, cover.fileName) && Objects.equals(pathToFullCover, cover.pathToFullCover);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fileName, pathToFullCover);
    }

    @Override
    public String toString() {
        return "Cover{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", pathToFullCover='" + pathToFullCover + '\'' +
                '}';
    }
}
