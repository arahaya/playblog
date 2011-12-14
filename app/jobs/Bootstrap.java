package jobs;

import play.jobs.Job;
import play.jobs.OnApplicationStart;

@OnApplicationStart
public class Bootstrap extends Job {
    public void doJob() {
        /*if (Play.mode.isDev() && models.Post.count() == 0) {
            Fixtures.loadModels("test-data.yml");
        }*/
    }
}
