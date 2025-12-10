import org.example.Solution7;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * L学号_X_Test.java
 *
 * 假设学号为 2023112947，题目编号为 7。
 * 测试类命名为 L2023112947_7_Test.java
 *
 * 测试用例设计总体原则：
 * 1.  **等价类划分：** 根据输入字符串s的特性（长度、字符种类）、pairs列表的特性（空、单对、多对、连接性）进行划分。
 * 2.  **边界值分析：** 字符串最短（1个字符）、pairs最少（0对）、pairs最多（接近s.length^2）、索引边界值（0, s.length-1）。
 * 3.  **特殊情况：** 所有字符相同、所有字符不同、所有字符可交换、部分字符可交换且形成多个连通分量、字符重复等。
 * 4.  **示例验证：** 确保代码能正确处理问题描述中给出的示例。
 */
public class L2023112947_7_Test {

    private org.example.Solution7 solution = new org.example.Solution7();

    /**
     * 辅助方法：将二维数组转换为 List<List<Integer>> 形式的 pairs
     *
     * @param rawPairs 原始的 int[][] 形式的索引对
     * @return 转换后的 List<List<Integer>>
     */
    private List<List<Integer>> createPairs(int[][] rawPairs) {
        List<List<Integer>> pairs = new ArrayList<>();
        for (int[] pair : rawPairs) {
            pairs.add(Arrays.asList(pair[0], pair[1]));
        }
        return pairs;
    }

    @Test
    public void testExample1() {
        String s = "dcab";
        List<List<Integer>> pairs = createPairs(new int[][]{{0, 3}, {1, 2}});
        assertEquals("bcad", solution.smallestStringWithSwaps(s, pairs));
    }

    @Test
    public void testExample2() {
        String s = "dcab";
        List<List<Integer>> pairs = createPairs(new int[][]{{0, 3}, {1, 2}, {0, 2}});
        assertEquals("abcd", solution.smallestStringWithSwaps(s, pairs));
    }

    @Test
    public void testExample3() {
        String s = "cba";
        List<List<Integer>> pairs = createPairs(new int[][]{{0, 1}, {1, 2}});
        assertEquals("abc", solution.smallestStringWithSwaps(s, pairs));
    }

    @Test
    public void testEmptyPairs() {
        String s = "abcdef";
        List<List<Integer>> pairs = new ArrayList<>();
        assertEquals("abcdef", solution.smallestStringWithSwaps(s, pairs));
    }

    @Test
    public void testSingleCharString() {
        String s = "z";
        List<List<Integer>> pairs = new ArrayList<>();
        assertEquals("z", solution.smallestStringWithSwaps(s, pairs));
    }

    @Test
    public void testSingleCharStringDegeneratePair() {
        String s = "a";
        List<List<Integer>> pairs = createPairs(new int[][]{{0, 0}});
        assertEquals("a", solution.smallestStringWithSwaps(s, pairs));
    }

    @Test
    public void testAllSwappable() {
        String s = "zyxw";
        List<List<Integer>> pairs = createPairs(new int[][]{{0, 1}, {1, 2}, {2, 3}});
        assertEquals("wxyz", solution.smallestStringWithSwaps(s, pairs));
    }

    @Test
    public void testDisjointComponents() {
        String s = "gfedcba";
        List<List<Integer>> pairs = createPairs(new int[][]{{0, 1}, {2, 3}, {4, 5}});
        assertEquals("fgdebca", solution.smallestStringWithSwaps(s, pairs));
    }

    @Test
    public void testDuplicatesWithSwaps() {
        String s = "banana";
        List<List<Integer>> pairs = createPairs(new int[][]{{0, 5}, {1, 3}});
        assertEquals("aananb", solution.smallestStringWithSwaps(s, pairs));
    }

    @Test
    public void testLongerStringPartialSwaps() {
        String s = "zyxwabcdefg";
        List<List<Integer>> pairs = createPairs(new int[][]{{0, 3}, {1, 2}, {4, 7}});
        assertEquals("wxyzabcdefg", solution.smallestStringWithSwaps(s, pairs));
    }

    @Test
    public void testAllSameChars() {
        String s = "aaaaa";
        List<List<Integer>> pairs = createPairs(new int[][]{{0, 4}, {1, 3}});
        assertEquals("aaaaa", solution.smallestStringWithSwaps(s, pairs));
    }

    @Test
    public void testComplexOverlappingSwaps() {
        String s = "gfedcba";
        List<List<Integer>> pairs = createPairs(new int[][]{{0, 1}, {1, 2}, {3, 4}, {4, 5}, {2, 3}});
        assertEquals("bcdefga", solution.smallestStringWithSwaps(s, pairs));
    }
}
