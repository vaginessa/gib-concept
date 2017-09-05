package io.github.alamo18.updater.model.json;

import java.util.ArrayList;
import java.util.List;

public class Response {

    private List<Repo> repolist = new ArrayList<>();

    public List<Repo> getRepolist() {
        return repolist;
    }
}
