package over.resources.exceptions;

import java.io.Serializable;
import java.time.Instant;

// O Serializable permite que os dados possam ser transformados em sequência de bytes
// e prevenir erros de rede
public class StandardError implements Serializable {

//    variáveis no padrão de JSON do Spring
    private Instant timeStamp;
    private Integer status;
    private String error;
    private String message;
    private String path;

    public StandardError(){
    }

    public Instant getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Instant timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
