import bagel.*;
import java.util.ArrayList;

/** Level Three in game. Contains all information and operations relevant to gameplay.
 * Extends regular level by adding enemy/guardian dynamic; enemies can steal notes, and the guardian
 * can fire projectiles to destroy enemies.
 * @author lpedersson
 * @version 1.0
 */
public class LevelThree extends Level {
    private static final Keys SHOOT_KEY = Keys.LEFT_SHIFT;
    private final Guardian guardian = new Guardian(this);
    private final ArrayList<Enemy> enemies = new ArrayList<>();
    private final ArrayList<Projectile> projectiles = new ArrayList<>();


    /** Creates new LevelThree instance.
     * @param levelNum Numerical code corresponding to level to be generated.*/
    public LevelThree(int levelNum) {
        super(levelNum);
    }

    /** Increment frame number and update each lane according to frame number and user input.
     * Then, check for collisions between enemies and notes or projectiles. If any occur, eliminate
     * appropriate entities. Update enemy and projectile entities.
     * @param input User input. */
    @Override
    public void update(Input input) {
        super.update(input);

        /* Introduce new enemy every 600 frames, except for initial frame. */
        if ((frameNum != 0) && (frameNum % Enemy.SPAWN_FREQ == 0)) {
            enemies.add(new Enemy(this));
        }

        /* If SHOOT_KEY is pressed, locate enemy nearest to guardian and fire projectile. */
        if (input.wasPressed(SHOOT_KEY)) {
            Enemy target = nearestEnemy();
            if (target != null) {
                projectiles.add(new Projectile(target, this));
            }
        }

        /* Check for collisions between enemies and projectiles. Eliminate both involved entities
         * where they occur. */
        checkCollisions(enemies, projectiles, Projectile.ENEMY_THRESHOLD, true);

        /* In each lane, check for collisions between enemies and notes. Eliminate notes where these occur. */
        for (Lane lane : lanes) {
            if (lane != null) {
                checkCollisions(enemies, lane.getNotes(), Enemy.NOTE_DIST_THRESHOLD, false);
            }
        }

        pruneArrayList(enemies);
        pruneArrayList(projectiles);
    }

    /** Check for collisions between elements of outerList and elements of innerList.
     * Detect collisions when entities come within distanceThreshold of one another. Only deactivate
     * entity belonging to outerList in event of collision of deactivateOuter true.
     * @param outerList Entities which we check collisions for.
     * @param innerList Entities that elements of outerList may collide with.
     * @param distanceThreshold Distance within which we detect collisions.
     * @param deactivateOuter Mark true if outer element should also be eliminated during collisions. False otherwise.
     */
    private void checkCollisions(ArrayList<? extends ActivatesAndHasDistance> outerList,
                                 ArrayList<? extends ActivatesAndHasPosition> innerList,
                                 int distanceThreshold, boolean deactivateOuter) {
        for (int i = 0; i< outerList.size(); i++) {
            for (ActivatesAndHasPosition innerObject : innerList) {
                if (innerObject == null || !innerObject.getActive()) {
                    continue;
                }
                double currDistance = outerList.get(i).distanceFrom(innerObject.getXPos(), innerObject.getYPos());
                if (currDistance < distanceThreshold) {
                    if (deactivateOuter) {
                        outerList.get(i).deactivate();
                        i--;
                    }
                    innerObject.deactivate();
                    break;
                }
            }
        }
    }

    /** Updates each element of input arrayList, and removes any inactive elements.
     * @param arrayList ArrayList to be pruned.
     */
    private void pruneArrayList(ArrayList<? extends Activates> arrayList) {
        for (int i=0; i<arrayList.size(); i++) {
            arrayList.get(i).update();
            if (!arrayList.get(i).getActive()) {
                arrayList.remove(i);
                i--;
            }
        }
    }

    /** Locates enemy nearest to guardian. Returns reference to this enemy.
     * @return Reference to enemy nearest to guardian.
     */
    private Enemy nearestEnemy() {
        double minDistance = Double.MAX_VALUE;
        double currDistance;
        Enemy nearestEnemy = null;
        for (Enemy enemy: enemies) {
            currDistance = enemy.distanceFrom(Guardian.X_POS, Guardian.Y_POS);
            if (currDistance < minDistance) {
                minDistance = currDistance;
                nearestEnemy = enemy;
            }
        }
        return nearestEnemy;
    }

    /** Render score counter, each lane, active notes in each lane, and any messages.
     * Also render guardian, any enemies, and any projectiles currently active.
     */
    public void render() {
        guardian.render();
        for (Enemy enemy : enemies) {
            enemy.render();
        }
        for (Projectile projectile: projectiles) {
            projectile.render();
        }
        super.render();
    }
}
