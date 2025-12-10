

package org.example;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * @description:
 *
 * 给你一个字符串 s，以及该字符串中的一些「索引对」数组 pairs，其中 pairs[i] = [a, b] 表示字符串中的两个索引（编号从 0 开始）。
 *
 * 你可以 任意多次交换 在 pairs 中任意一对索引处的字符。
 *
 * 返回在经过若干次交换后，s 可以变成的按字典序最小的字符串。
 *
 *
 *
 * 示例 1:
 *
 * 输入：s = "dcab", pairs = [[0,3],[1,2]]
 * 输出："bacd"
 * 解释：
 * 交换 s[0] 和 s[3], s = "bcad"
 * 交换 s[1] 和 s[2], s = "bacd"
 * 示例 2：
 *
 * 输入：s = "dcab", pairs = [[0,3],[1,2],[0,2]]
 * 输出："abcd"
 * 解释：
 * 交换 s[0] 和 s[3], s = "bcad"
 * 交换 s[0] 和 s[2], s = "acbd"
 * 交换 s[1] 和 s[2], s = "abcd"
 * 示例 3：
 *
 * 输入：s = "cba", pairs = [[0,1],[1,2]]
 * 输出："abc"
 * 解释：
 * 交换 s[0] 和 s[1], s = "bca"
 * 交换 s[1] 和 s[2], s = "bac"
 * 交换 s[0] 和 s[1], s = "abc"
 *
 *
 * 提示：
 *
 * 1 <= s.length <= 10^5
 * 0 <= pairs.length <= 10^5
 * 0 <= pairs[i][0], pairs[i][1] < s.length
 * s 中只含有小写英文字母
 *
 */

public class Solution7 {

    public String smallestStringWithSwaps(String s, List<List<Integer>> pairs) {

        // BUG FIX 2: 移除不必要的初始条件判断。
        // 即使 pairs 为空，下面的并查集逻辑也会正确处理：
        // 每个索引都是一个独立的连通分量，字符会按原样放回，结果就是原始字符串 s。
        // if (pairs.size() <= 1) {
        //     return s;
        // }

        int len = s.length();
        // BUG FIX 1: UnionFind 构造函数应该传入字符串的长度 len，因为索引范围是 0 到 len-1。
        UnionFind unionFind = new UnionFind(len);

        // 第 1 步：将任意交换的结点对输入并查集
        for (List<Integer> pair : pairs) {
            int index1 = pair.get(0);
            int index2 = pair.get(1);
            unionFind.union(index1, index2);
        }

        // 第 2 步：构建映射关系
        char[] charArray = s.toCharArray();
        // key：连通分量的代表元，value：同一个连通分量的字符（保存在一个优先队列中）
        // 优先队列会自动将字符按字典序排序，每次 poll() 都会取出最小的字符。
        Map<Integer, PriorityQueue<Character>> hashMap = new HashMap<>(len);
        for (int i = 0; i < len; i++) { // BUG FIX 3: 为 for 循环体添加大括号 {}
            int root = unionFind.find(i);
            hashMap.computeIfAbsent(root, key -> new PriorityQueue<>()).offer(charArray[i]);
        }

        // 第 3 步：重组字符串
        StringBuilder stringBuilder = new StringBuilder(len); // 优化：预设 StringBuilder 容量
        for (int i = 0; i < len; i++) {
            int root = unionFind.find(i);
            stringBuilder.append(hashMap.get(root).poll());
            // BUG FIX 4: 移除多余的空格追加操作。
            // stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }

    private static class UnionFind {

        private int[] parent;
        /**
         * 以 i 为根结点的子树的高度（引入了路径压缩以后该定义并不准确，更准确地说是“秩”或“层级”）
         * 用于优化 union 操作，避免树变得过高。
         */
        private int[] rank;

        public UnionFind(int n) {
            this.parent = new int[n];
            this.rank = new int[n];
            for (int i = 0; i < n; i++) {
                this.parent[i] = i; // 初始化时，每个元素都是自己的父节点
                this.rank[i] = 1;   // 初始化时，每个树的高度（秩）为 1
            }
        }

        public void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);
            if (rootX == rootY) {
                return; // 已经在同一个集合中
            }

            // 按秩合并（union by rank）：将较低秩的树连接到较高秩的树的根上
            if (rank[rootX] < rank[rootY]) {
                parent[rootX] = rootY;
            } else if (rank[rootX] > rank[rootY]) {
                parent[rootY] = rootX;
            } else { // 如果秩相等，则任意选择一个作为新的根，并将其秩加 1
                parent[rootY] = rootX;
                rank[rootX]++;
            }
        }

        public int find(int x) {
            // 路径压缩（path compression）：在查找根节点的同时，将路径上的所有节点直接指向根节点
            if (x != parent[x]) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }
    }
}
