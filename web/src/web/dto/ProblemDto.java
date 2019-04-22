package web.dto;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProblemDto extends BaseDto {
    private String subject;
    private String difficulty;
    private String text;

    @Override
    public String toString() {
        return "ProblemDto{" +
                "subject='" + subject + '\'' +
                ", difficulty='" + difficulty + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
