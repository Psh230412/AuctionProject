import java.time.LocalDateTime;
import java.util.List;

public interface ITimer {
	// 이미지(image MEDIUMBL CB), 
	// 버튼, 
	// 남은시간(deadline DATETIME - now LocalDate), 
	// 현재가격(bidprice)을 하나의 판넬로
	LocalDateTime selectTime(int butten);
	int updateTime(int butten);
	List<Product> selectProductId(int userId);
	List<Product> selectProduct();
}