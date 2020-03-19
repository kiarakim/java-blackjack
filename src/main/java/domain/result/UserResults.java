package domain.result;

import static java.util.stream.Collectors.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import domain.user.User;

public class UserResults {
	private static final String EMPTY_USER_RESULT_EXCEPTION_MESSAGE = "인자 값이 비어있으면 안됩니다.";
	private final List<UserResult> playerResults;
	private final UserResult dealerResult;

	private UserResults(List<UserResult> userResults, UserResult dealerResult) {
		this.playerResults = Collections.unmodifiableList(new ArrayList<>(Objects.requireNonNull(userResults)));
		this.dealerResult = Objects.requireNonNull(dealerResult);
	}

	public static UserResults fromPlayerResultsAndDealer(List<UserResult> userResults, User dealer) {
		UserResult dealerResult = initDealerResult(userResults, dealer);
		return new UserResults(userResults, dealerResult);
	}

	private static UserResult initDealerResult(List<UserResult> userResults, User dealer) {
		return new UserResult(dealer, calculateDealerTotalPrize(userResults));
	}

	private static Prize calculateDealerTotalPrize(List<UserResult> userResults) {
		return userResults.stream()
			.map(UserResult::getPrize)
			.map(Prize::calculateDealerPrize)
			.reduce(Prize::sum)
			.orElseThrow(() -> new IllegalArgumentException(EMPTY_USER_RESULT_EXCEPTION_MESSAGE));
	}

	public List<UserResult> getPlayerResults() {
		return Stream.concat(Stream.of(dealerResult), playerResults.stream())
			.collect(collectingAndThen(toList(), Collections::unmodifiableList));
	}
}
