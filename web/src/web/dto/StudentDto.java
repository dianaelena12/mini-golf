package web.dto;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StudentDto extends BaseDto {
    private String serialnumber;
    private String name;
    private int gr;

    @Override
    public String toString() {
        return "StudentDto{" +
                "serialnumber='" + serialnumber + '\'' +
                ", name='" + name + '\'' +
                ", gr=" + gr +
                '}';
    }
}
