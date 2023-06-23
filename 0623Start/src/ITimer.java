import java.time.LocalDateTime;

public interface ITimer {
	LocalDateTime select(int butten);
	int update(int butten);
}