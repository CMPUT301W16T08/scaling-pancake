package cmput301w16t08.scaling_pancake.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

/**
 * <code>Instrument</code> is meant represent an instrument owned by a specific <code>User</code>
 * (called the owner), and contains a list of <code>Bid</code>s on the instrument
 *
 * @author William
 * @see User
 * @see Bid
 */
public class Instrument {
    private String status;
    private String name;
    private String description;
    private String ownerId;
    private String borrowedById;
    private BidList bids;
    private String id;
    private boolean returnedFlag;
    protected transient Bitmap thumbnail;
    protected String thumbnailBase64;


    /**
     * Creates a new <code>Instrument</code> with supplied name and description, and for supplied owner
     *
     * @param owner the id of the owner of the instrument
     * @param name the name of the instrument
     * @param description the description of the instrument
     */
    public Instrument(String owner, String name, String description) {
        this.name = name;
        this.description = description;
        this.ownerId = owner;
        this.status = "available";
        this.borrowedById = null;
        this.bids = new BidList();
        this.id = UUID.randomUUID().toString();
        this.thumbnail = null;
        this.thumbnailBase64 = null;
        this.returnedFlag = false;
    }

    /**
     * Creates a new <code>Instrument</code> with supplied name, description, and thumbnail, and for supplied owner
     *
     * @param owner the id of the owner of the instrument
     * @param name the name of the instrument
     * @param description the description of the instrument
     * @param thumbnail the image for the instrument
     */
    public Instrument(String owner, String name, String description, Bitmap thumbnail) {
        this.name = name;
        this.description = description;
        this.ownerId = owner;
        this.status = "available";
        this.borrowedById = null;
        this.bids = new BidList();
        this.id = UUID.randomUUID().toString();
        this.returnedFlag = false;
        addThumbnail(thumbnail);

    }

    /**
     * Creates the <code>Instrument</code> with the supplied id
     *
     * @param owner the id of the owner of the instrument
     * @param name the name of the instrument
     * @param description the description of the instrument
     * @param id the id of the instrument
     */
    public Instrument(String owner, String name, String description, String id, Bitmap thumbnail, boolean returned) {
        this.name = name;
        this.description = description;
        this.ownerId = owner;
        this.status = "available";
        this.borrowedById = null;
        this.bids = new BidList();
        this.id = id;
        this.returnedFlag = returned;
        addThumbnail(thumbnail);
    }

    /**
     * Creates the <code>Instrument</code> with the supplied id
     *
     * @param owner the id of the owner of the instrument
     * @param name the name of the instrument
     * @param description the description of the instrument
     * @param id the id of the instrument
     */
    public Instrument(String owner, String name, String description, String id, boolean returned) {
        this.name = name;
        this.description = description;
        this.ownerId = owner;
        this.status = "available";
        this.borrowedById = null;
        this.bids = new BidList();
        this.id = id;
        this.thumbnail = null;
        this.thumbnailBase64 = null;
        this.returnedFlag = returned;
    }

    /**
     * Returns the status of <code>Instrument</code>
     * Is either "available", "bidded", or "borrowed"
     *
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status of the <code>Instrument</code>
     *
     * @param status the new status
     * @throws RuntimeException
     *          if status not "available", "bidded", or "borrowed"
     */
    public void setStatus(String status) {
        if (status.equals("available") || status.equals("bidded") || status.equals("borrowed")) {
            this.status = status;
        } else { throw new RuntimeException("status should be one of available, bidded, or borrowed"); }
    }

    /**
     * Returns the name of the <code>Instrument</code>
     *
     * @return the instrument's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the <code>Instrument</code>
     *
     * @param name the new name for the instrument
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the description of the <code>Instrument</code>
     *
     * @return the description of the instrument
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the <code>Instrument</code>
     *
     * @param description the new description for the instrument
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the id of the <code>User</code> that owns the <code>Instrument</code>
     *
     * @return id of the owner
     */
    public String getOwnerId() {
        return ownerId;
    }

    /**
     * Returns the id of the <code>User</code> that is borrowing the <code>Instrument</code>
     *
     * @return the id of the borrower
     * @throws RuntimeException
     *          if the status of the instrument is not currently "borrowed"
     */
    public String getBorrowedById() {
        if (!this.status.equals("borrowed")) {
            throw new RuntimeException("The instrument is not currently borrowed");
        }
        return borrowedById;
    }

    /**
     * Sets the id of the <code>User</code> that is borrowing the <code>Instrument</code>
     *
     * @param borrowedBy the id of the user that is borrowing the instrument
     */
    public void setBorrowedById(String borrowedBy) {
        this.borrowedById = borrowedBy;
        this.status = "borrowed";
    }

    /**
     * Returns the <code>Bid</code>s on the <code>Instrument</code>
     *
     * @return the bids
     */
    public BidList getBids() {
        return this.bids;
    }

    /**
     * Adds a <code>Bid</code> to the list of <code>Bid</code>s on the <code>Instrument</code>
     *
     * @param bid the bid to add
     */
    public void addBid(Bid bid) {
        this.bids.addBid(bid);
        this.setStatus("bidded");
    }

    /**
     * Accepts a <code>Bid</code> on the <code>Instrument</code>
     *
     * @param bid the bid to accept
     * @throws RuntimeException
     *          if the bid is not contained in the instrument's bids
     */
    public void acceptBid(Bid bid) {
        if (this.bids.containsBid(bid)) {
            bid.setAccepted();
            this.bids.clearBids();
            this.bids.addBid(bid);
            this.setStatus("borrowed");
            this.setBorrowedById(bid.getBidderId());
        }
        else {
            throw new RuntimeException();
        }
    }

    /**
     * Accepts the <code>Bid</code> at the supplied index of the list of <code>Bid</code>s on the <code>Instrument</code>
     *
     * @param index the index of the bid to accept
     * @throws RuntimeException
     *          if the index is not in range <code>0</code> to <code>size()-1</code>
     */
    public void acceptBid(int index) {
        if (index < this.bids.size() && index >= 0) {
            Bid bid = this.bids.getBid(index);
            bid.setAccepted();
            this.bids.clearBids();
            this.bids.addBid(bid);
            this.setStatus("borrowed");
            this.setBorrowedById(bid.getBidderId());
        }
        else {
            throw new RuntimeException();
        }
    }

    /**
     * Declines a <code>Bid</code> on the <code>Instrument</code>
     *
     * @param bid the bid to decline
     * @throws RuntimeException
     *          if the bid is not contained in the instrument's bids
     */
    public void declineBid(Bid bid) {
        if (this.bids.containsBid(bid)) {
            this.bids.removeBid(bid);
            if (this.bids.size() == 0) {
                this.setStatus("available");
            }
        }
        else {
            throw new RuntimeException();
        }
    }

    /**
     * Declines the <code>Bid</code> at the supplied index on the <code>Instrument</code>
     *
     * @param index the index of the bid to decline
     * @throws RuntimeException
     *          if the index is not in range <code>0</code> to <code>size()-1</code>
     */
    public void declineBid(int index) {
        if (index < this.bids.size()) {
            this.bids.removeBid(this.bids.getBid(index));
            if (this.bids.size() == 0) {
                this.setStatus("available");
            }
        }
        else {
            throw new RuntimeException();
        }
    }

    /**
     * Returns the id of the <code>Bid</code>
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Returns true if <code>Instrument</code> recently returned by borrower
     *
     * @return true/false
     */
    public boolean getReturnedFlag() {
        return this.returnedFlag;
    }

    /**
     * Sets the returnedFlag on the <code>Instrument</code>
     *
     * @param b boolean for if instrument has been returned recently by borrower
     */
    public void setReturnedFlag(boolean b) {
        this.returnedFlag = b;
    }

    /**
     * Adds a thumbnail for the <code>Instrument</code>
     *
     * @param thumbnail the new thumbnail for the instrument
     */
    public void addThumbnail(Bitmap thumbnail) {
        if (thumbnail != null) {
            this.thumbnail = thumbnail;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            thumbnail.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] b = byteArrayOutputStream.toByteArray();
            this.thumbnailBase64 = Base64.encodeToString(b, Base64.DEFAULT);
        }
    }

    /**
     * Adds a thumbnail for the <code>Instrument</code> as a Base64 String
     *
     * @param thumbnailBase64 the new thumbnail in Base64
     */
    public void addThumbnail(String thumbnailBase64) {
        if (thumbnailBase64 != null && !thumbnailBase64.equals("")) {
            this.thumbnailBase64 = thumbnailBase64;
            byte[] b = Base64.decode(thumbnailBase64.getBytes(), 0);
            this.thumbnail = BitmapFactory.decodeByteArray(b, 0, b.length);
        }
    }

    /**
     * Returns the thumbnail for the <code>Instrument</code>
     *
     * @return the thumbnail, or null if no thumbnail
     */
    public Bitmap getThumbnail() {
        if (this.thumbnail == null && thumbnailBase64 != null) {
            byte[] decodeString = Base64.decode(thumbnailBase64, Base64.DEFAULT);
            this.thumbnail = BitmapFactory.decodeByteArray(decodeString, 0, decodeString.length);
        }
        return this.thumbnail;
    }

    /**
     * Returns the thumbnail for the <code>Instrument</code> as a String
     *
     * @return the thumbnail as a String, or null if no thumbnail
     */
    public String getThumbnailBase64() {
        return this.thumbnailBase64;
    }

    /**
     * Deletes the thumbnail attached to the <code>Instrument</code>
     */
    public void deleteThumbnail() {
        this.thumbnail = null;
        this.thumbnailBase64 = null;
    }
}
