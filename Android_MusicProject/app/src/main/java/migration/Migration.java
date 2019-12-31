package migration;

import io.realm.DynamicRealm;
import io.realm.RealmList;
import io.realm.RealmMigration;
import io.realm.RealmSchema;
import models.AlbumModel;

public class Migration implements RealmMigration {
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {

        RealmSchema schma = realm.getSchema();

        if (oldVersion == 0){

            schma.create("MusicModel")
                    .addField("musicId",String.class)
                    .addField("name",String.class)
                    .addField("poster",String.class)
                    .addField("path",String.class)
                    .addField("author",String.class);

            schma.create("AlbumModel")
                    .addField("albumId",String.class)
                    .addField("name",String.class)
                    .addField("poster",String.class)
                    .addField("playNum",String.class)
                    .addRealmListField("list",schma.get("MusicModel"));

            schma.create("MusicSourceModel")
                    .addRealmListField("album",schma.get("AlbumModel"))
                    .addRealmListField("hot",schma.get("MusicModel"));

            oldVersion = newVersion;
        }
    }
}
