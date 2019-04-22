package web.dto;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AssignmentDto extends BaseDto {
    private Long studentID, problemID;
    private int grade;

    @Override
    public String toString() {
        return "AssignmentDto{" +
                "studentID=" + studentID +
                ", problemID=" + problemID +
                ", grade=" + grade +
                '}';
    }
}
