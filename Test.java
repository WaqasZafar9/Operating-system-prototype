import java.util.*;

public class Test {
    static String[] framesRecord;
    static int frame = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of frames: ");
        int capacity = scanner.nextInt();

        LRUCache lruCache = new LRUCache(capacity);

        System.out.print("Enter the reference string (space-separated numbers): ");
        scanner.nextLine(); // Consume the newline character
        String[] references = scanner.nextLine().split("\\s+");
        framesRecord = new String[references.length];

        int pageFaults = 0;
        for (String reference : references) {
            int page = Integer.parseInt(reference);
            if (!lruCache.referencePage(page)) {
                pageFaults++;
            }
            lruCache.printFrames(pageFaults);
            System.out.println();
        }

        System.out.println("The total number of page faults using LRU is: " + pageFaults);
        lruCache.print();
    }

    static class LRUCache {
        private final LinkedHashMap<Integer, Integer> cache;
        private final int capacity;

        public LRUCache(int capacity) {
            this.capacity = capacity;
            this.cache = new LinkedHashMap<>(capacity, 0.75f, true);
        }

        public boolean referencePage(int page) {
            if (cache.containsKey(page)) {
                // Page is already in the cache, move it to the front (most recently used)
                cache.remove(page);
                cache.put(page, 0);
                return true;
            }

            if (cache.size() >= capacity) {
                // Cache is full, remove the least recently used page
                Iterator<Map.Entry<Integer, Integer>> iterator = cache.entrySet().iterator();
                iterator.next();
                iterator.remove();
            }

            // Add the new page to the cache
            cache.put(page, 0);
            return false;
        }

        public void printFrames(int fault) {
            System.out.print("Frames: ");
            for (int key : cache.keySet()) {
                framesRecord[frame] += key + ",";
                System.out.print(key + " ");
            }
            framesRecord[frame] += fault + ",";
            frame++;
        }

        void print(){
            for (String a:framesRecord){
                System.out.println(a);
            }
        }
    }
}
