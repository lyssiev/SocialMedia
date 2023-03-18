package socialmedia;

public class Endorsement extends Post {
        private int originalPostId;

        public Endorsement(String handle, String message, int originalPostId) {
            super(handle, message);
            this.originalPostId = originalPostId;
            this.actionable = false;
        }

        @Override
        public void addEndorsement(Endorsement endorsement) {
            // do nothing, cannot be endorsed
        }

        @Override
        public void addComment(Comment comment) {
            // do nothing, cannot be commented
        }


    }

