package net.heroyn.heroynserverapi.utils;

public enum Skin
{
    HEROBRINE("HEROBRINE", 0, "eyJ0aW1lc3RhbXAiOjE0OTIzNDI0NzE3NjcsInByb2ZpbGVJZCI6ImY4NGM2YTc5MGE0ZTQ1ZTA4NzliY2Q0OWViZDRjNGUyIiwicHJvZmlsZU5hbWUiOiJIZXJvYnJpbmUiLCJzaWduYXR1cmVSZXF1aXJlZCI6dHJ1ZSwidGV4dHVyZXMiOnsiU0tJTiI6eyJ1cmwiOiJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2JkZDNjYzhhZTI5YTRkZmU1NjVkZGNkN2E2NjMzODhkOGVhZDdmNWJjOGRiYTVhNDkzMTQ4YjI0NTk4NGQifX19", "J1537dRxCJT1Up2LjBnM8sR2HERJCIZ1LWSUFYpMLSq7QBO/ij4Wi++SlerneUB3aOAAP3CC4cI1oWkJF3B4pDohooQo9B+7QAKFa0QFkaTIev9+x2oUYe9L/jbSanGDNzKjQE62FgN66yIlNOiO9j1off8anpjmq8kDGAJRR6OFOepFjJLUl2l/G3VWXgRK2KfG/5sdEJKOyk/R9LYLr64ER9bIsBScvaGykgOrCwe6sBU4AFZ9iyQDdSYtZ79aw1788aMzWn9m4O6qGHodzAORiQ3uDD1IR7bq4lMCCY4UyHH21jvl3VrsvR8rjL0vPJN0M3Giz4OubacpZirea/GMFyjOWu7IqUi4OUwwJ1UcxWtNZKZb+stg7i3kycnxpjWg1uE7cXTDXbuTqKDzuPzRDJNUgeWlVKt45/VEIkUCmjxrOzmFGxUbq79p3DbA+pIFbsS/ePpbpWq9TYEuV9lP0DMW7EzkDg/oar3s/pHrtTbkuBIfpD4UqwAT6LezWxOQef//EBba64PI5CP97KkcrTkTu7zAXTq9faNyD9y9VA/Bteh/avwdMTYhFiRxAVGc8tSk6MBatII4mEEtdhxEIvFXL9PmEnK0InHp4tkLGLZBq4/elq+X46So4sFuYG9hz5b/NgJt2sEsKLGRDWXSjklcapxW9RjUKHxuUvM=");
    
    private String texture;
    private String signature;
    
    private Skin(final String s, final int n, final String s2, final String signature) {
        this.texture = String.valueOf(CC.headkey) + s2;
        this.signature = signature;
    }
    
    public String getTexture() {
        return this.texture;
    }
    
    public String getSignature() {
        return this.signature;
    }
}
