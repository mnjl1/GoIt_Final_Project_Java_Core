import java.util.Comparator;

//object for sort result
public class SortResultObject {

     String fillMethodName;
     String sortMethodName;
     int collectionSize;
     long sortTime;

    public SortResultObject(String fillMethodName, String sortMethodName,
                            int collectionSize, long sortTime) {
        this.fillMethodName = fillMethodName;
        this.sortMethodName = sortMethodName;
        this.collectionSize = collectionSize;
        this.sortTime = sortTime;
    }
    public SortResultObject(){

    }

    public String getFillMethodName() {
        return fillMethodName;
    }

    public void setFillMethodName(String fillMethodName) {
        this.fillMethodName = fillMethodName;
    }

    public String getSortMethodName() {
        return sortMethodName;
    }

    public void setSortMethodName(String sortMethodName) {
        this.sortMethodName = sortMethodName;
    }

    public int getCollectionSize() {
        return collectionSize;
    }

    public void setCollectionSize(int collectionSize) {
        this.collectionSize = collectionSize;
    }

    public long getSortTime() {
        return sortTime;
    }

    public void setSortTime(long sortTime) {
        this.sortTime = sortTime;
    }

    //just for testing "print collection"
    @Override
    public String toString() {
        return "SortResultObject{" +
                "fillMethodName='" + fillMethodName + '\'' +
                ", sortMethodName='" + sortMethodName + '\'' +
                ", collectionSize=" + collectionSize +
                ", sortTime=" + sortTime +
                '}';
    }
}

