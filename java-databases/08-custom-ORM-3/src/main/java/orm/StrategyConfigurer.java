package orm;

import orm.strategies.DropCreateStrategy;
import orm.strategies.UpdateStrategy;

public class StrategyConfigurer {

    private EntityManagerBuilder entityManagerBuilder;

    public StrategyConfigurer(EntityManagerBuilder entityManagerBuilder) {
        this.entityManagerBuilder = entityManagerBuilder;
    }

    public EntityManagerBuilder setDropCreateStrategy() {
        this.entityManagerBuilder.setStrategy(new DropCreateStrategy());

        return this.entityManagerBuilder;
    }

    public EntityManagerBuilder setUpdateStrategy() {
        this.entityManagerBuilder.setStrategy(new UpdateStrategy());

        return this.entityManagerBuilder;
    }


}
