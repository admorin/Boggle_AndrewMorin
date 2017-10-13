/**
 * Andrew Morin
 * October 10, 2017
 */

/**
 * The trie, composed of trie nodes, is a type of recursion that allows me to search through my
 * entire board very quickly. I stumbled across this method while looking up how to search a 2D array.
 * Although this is a pretty commonly implemented method, I will explain it. The trie, also called a prefix
 * tree, will start at a given point and work it's way through the 2D array. At every starting point, it
 * branches out from the first letter and constantly checks to see if that word is a real word in the dictionary,
 * which was fed to it as a linked list of strings (I made this list in the library class). It can optimize
 * it's search by reusing the same prefixes. For example, bee and beer would have the same prefix and beer would
 * technically only add one more letter to create a new word, instead of being 4 individual new characters.
 */

//Trie Node
class TrieNode{
    public TrieNode[] children = new TrieNode[26]; //26 letters of the alphabet, I force lowercase elsewhere.
    public String item = "";
}

//Trie
class Trie{
    public TrieNode root = new TrieNode();

    public void insert(String word){
        TrieNode node = root;
        //I have to subtract 'a' from the character to make sure the values are correct.
        for(char c: word.toCharArray()){
            if(node.children[c-'a']==null){
                node.children[c-'a']= new TrieNode();
            }
            node = node.children[c-'a'];
        }
        node.item = word;
    }

    //The primary search method for the nodes.
    public boolean search(String word){
        TrieNode node = root;
        for(char c: word.toCharArray()){
            if(node.children[c-'a']==null)
                return false;
            node = node.children[c-'a'];
        }
        return node.item.equals(word);
    }

    //The prefix method to the nodes.
    public boolean startsWith(String prefix){
        TrieNode node = root;
        for(char c: prefix.toCharArray()){
            if(node.children[c-'a']==null)
                return false;
            node = node.children[c-'a'];
        }
        return true;
    }
}