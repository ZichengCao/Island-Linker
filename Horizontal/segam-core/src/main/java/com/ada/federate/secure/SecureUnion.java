package com.ada.federate.secure;

import com.ada.federate.cache.FullKeyListMap;
import com.ada.federate.utils.Tools;

import java.util.*;

public class SecureUnion {


    public static class SecureUnionCache {

        private Set<String> localSet;

        private Set<String> localRandomSet = new HashSet<>();

        public SecureUnionCache(Set<String> localSet) {
            this.localSet = localSet;
        }


        private Set<String> getRandomSet(FullKeyListMap fullKeyListMap) {
            int count = Math.round(localSet.size());

            int[] index = Tools.generateNonRepetitiveRandomNum(1, count, count / 10);
            for (int i : index) {
                localRandomSet.add(fullKeyListMap.findPermutationElement(i));
            }
            // localRandomSet.addAll(Arrays.stream(index).boxed().collect(Collectors.toList()));
            return localRandomSet;
        }

        public Set<String> getLocalRandomSet() {
            return localRandomSet;
        }

        public boolean localRandomSetPop(String key) {
            if (localRandomSet.contains(key)) {
                localRandomSet.remove(key);
                return true;
            }
            return false;
        }

        public List<String> getConfusionSet(FullKeyListMap fullKeyListMap) {
            List<String> finalSet = new ArrayList<>(localSet);
            getRandomSet(fullKeyListMap);
            finalSet.addAll(localRandomSet);
            // LogUtils.debug(finalSet.toString());
            return finalSet;
        }

        public void clean() {
            if (localSet != null)
                localSet.clear();
            localRandomSet.clear();
        }
    }
}
