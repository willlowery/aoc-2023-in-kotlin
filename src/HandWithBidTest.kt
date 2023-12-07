import org.junit.jupiter.api.Test

class HandWithBidTest {

    @Test
    fun testBid() {
        assert(HandWithBid(1, "AAAAA").rankType == HandRank.FIVE_OF_A_KIND)
        assert(HandWithBid(1, "AAAAB").rankType == HandRank.FOUR_OF_A_KIND)
        assert(HandWithBid(1, "AAABB").rankType == HandRank.FULL_HOUSE)
        assert(HandWithBid(1, "AAABC").rankType == HandRank.THREE_OF_A_KIND)
        assert(HandWithBid(1, "AABBC").rankType == HandRank.TWO_PAIR)
        assert(HandWithBid(1, "AABCD").rankType == HandRank.ONE_PAIR)
        assert(HandWithBid(1, "ABCDE").rankType == HandRank.HIGH_CARD)
    }
}