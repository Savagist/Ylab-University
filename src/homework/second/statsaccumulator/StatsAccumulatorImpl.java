package homework.second.statsaccumulator;

public class StatsAccumulatorImpl implements StatsAccumulator {
    private Integer count;
    private Integer min;
    private Integer max;
    private Double avg;

    public StatsAccumulatorImpl() {
        this.count = 0;
        this.min = null;
        this.max = null;
        this.avg = null;
    }

    @Override
    public void add(int value) {
        count++;
        if (min == null) {
            min = value;
        } else {
            min = Math.min(min, value);
        }
        if (max == null) {
            max = value;
        } else {
            max = Math.max(min, value);
        }
        if (avg == null) {
            avg = (double) value;
        }
        else {
            avg = (avg * (count - 1) + value) / count;
        }
    }

    @Override
    public int getMin() {
        if (count == 0) {
            throw new IllegalStateException("Элемент еще не был добавлен, добавьте элемент и попробуйте снова");
        }
        return min;
    }

    @Override
    public int getMax() {
        if (count == 0) {
            throw new IllegalStateException("Элемент еще не был добавлен, добавьте элемент и попробуйте снова");
        }
        return max;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Double getAvg() {
        if (count == 0) {
            throw new IllegalStateException("Элемент еще не был добавлен, добавьте элемент и попробуйте снова");
        }
        return avg;
    }
}
