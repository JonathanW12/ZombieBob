package dk.sdu.mmmi.cbse.common.data.entityparts;

/**
 * Used for entites with timerProperties
 */

public class TimerPart implements EntityPart{

        private float expiration;

        public TimerPart(float expiration) {
            this.expiration = expiration;
        }

        public float getExpiration() {
            return expiration;
        }

        public void setExpiration(float expiration) {
            this.expiration = expiration;
        }

        public void reduceExpiration(float delta) {
            this.expiration -= delta;
        }



    }

