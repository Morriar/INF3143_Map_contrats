/*
 * Copyright 2016 Alexandre Terrasa <alexandre@moz-code.org>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package map;

import com.google.java.contract.Ensures;
import com.google.java.contract.Requires;
import java.util.ArrayList;

/**
 * Simple Map implementation.
 *
 * @param <K> keys type
 * @param <V> values type
 */
public class Map<K, V> {

    private ArrayList<MapNode<K, V>> internalNodes;

    /**
     * Create a new empty Map.
     */
    @Ensures("size() == 0")
    public Map() {
        internalNodes = new ArrayList<>();
    }

    /**
     * Is the Map empty?
     *
     * @return true is the Map is empty
     */
    @Ensures("result == (size() == 0)")
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Get the Map size.
     *
     * @return the number of elements in the Map
     */
    public int size() {
        return internalNodes.size();
    }

    /**
     * Get the value stored at the key.
     *
     * @param k the key to lookup
     * @return the value associated with the key
     */
    @Requires("hasKey(k)")
    public V get(K k) {
        for (MapNode<K, V> node : internalNodes) {
            if (node.key.equals(k)) {
                return node.value;
            }
        }
        return null;
    }

    /**
     * Put a value in the Map.
     *
     * @param k the key to add
     * @param v the value associated to the key
     */
    @Requires({
        "k != null", // k not null
        "!hasKey(k)" // k not already here
    })
    @Ensures({
        "hasKey(k)", // has key
        "get(k).equals(v)" // has value
    })
    public void put(K k, V v) {
        internalNodes.add(new MapNode<>(k, v));
    }

    /**
     * Remove a key from the Map.
     *
     * @param k the key to remove
     */
    @Requires({
        "k != null", // k not null
        "hasKey(k)" // k exists
    })
    @Ensures("!hasKey(k)")
    public void remove(K k) {
        for (int i = 0; i < internalNodes.size(); i++) {
            MapNode<K, V> node = internalNodes.get(i);
            if (node.key.equals(k)) {
                internalNodes.remove(i);
            }
        }
    }

    /**
     * Does this Map contain a specific key?
     *
     * @param k the key to lookup
     * @return true if the key exists in the Map
     */
    @Requires("k != null")
    public boolean hasKey(K k) {
        for (MapNode<K, V> node : internalNodes) {
            if (node.key.equals(k)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Internal Map node.
     *
     * @param <K> the Map keys type
     * @param <V> the Map values type
     */
    private class MapNode<K, V> {

        K key;
        V value;

        public MapNode(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
