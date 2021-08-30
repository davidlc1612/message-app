package com.example.reddit;

import java.util.Comparator;

public class Post
{
    private int votes = 0;
    private String parent = "", author, msg, key = "";

    public Post() {} // Crash Prevention
    public Post(String a, String m, String p) {author = a; msg = m; parent = p;}

    public String getParent() {return parent;}
    public String getAuthor() {return author;}
    public String getMsg() {return msg;}
    public int getVotes() {return votes;}
    public String getKey() {return key;}

    public void upVote() {votes++;}
    public void setKey(String k) {key = k;}

    static class Comparing implements Comparator<Post>
    {
        @Override
        public int compare(Post o1, Post o2) {return o2.getVotes() - o1.getVotes();}
    }
}
