/**
 * Autogenerated by Avro
 * 
 * DO NOT EDIT DIRECTLY
 */
package edu.scnu;  
@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public class ArticleFile extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"ArticleFile\",\"namespace\":\"edu.scnu\",\"fields\":[{\"name\":\"title\",\"type\":\"string\"},{\"name\":\"content_bytes\",\"type\":\"bytes\"},{\"name\":\"crc32\",\"type\":\"string\"},{\"name\":\"md5\",\"type\":\"string\"},{\"name\":\"last_mod_time\",\"type\":\"long\"},{\"name\":\"prev_mod_time\",\"type\":\"long\"}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }
  @Deprecated public java.lang.CharSequence title;
  @Deprecated public java.nio.ByteBuffer content_bytes;
  @Deprecated public java.lang.CharSequence crc32;
  @Deprecated public java.lang.CharSequence md5;
  @Deprecated public long last_mod_time;
  @Deprecated public long prev_mod_time;

  /**
   * Default constructor.
   */
  public ArticleFile() {}

  /**
   * All-args constructor.
   */
  public ArticleFile(java.lang.CharSequence title, java.nio.ByteBuffer content_bytes, java.lang.CharSequence crc32, java.lang.CharSequence md5, java.lang.Long last_mod_time, java.lang.Long prev_mod_time) {
    this.title = title;
    this.content_bytes = content_bytes;
    this.crc32 = crc32;
    this.md5 = md5;
    this.last_mod_time = last_mod_time;
    this.prev_mod_time = prev_mod_time;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call. 
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return title;
    case 1: return content_bytes;
    case 2: return crc32;
    case 3: return md5;
    case 4: return last_mod_time;
    case 5: return prev_mod_time;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }
  // Used by DatumReader.  Applications should not call. 
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: title = (java.lang.CharSequence)value$; break;
    case 1: content_bytes = (java.nio.ByteBuffer)value$; break;
    case 2: crc32 = (java.lang.CharSequence)value$; break;
    case 3: md5 = (java.lang.CharSequence)value$; break;
    case 4: last_mod_time = (java.lang.Long)value$; break;
    case 5: prev_mod_time = (java.lang.Long)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'title' field.
   */
  public java.lang.CharSequence getTitle() {
    return title;
  }

  /**
   * Sets the value of the 'title' field.
   * @param value the value to set.
   */
  public void setTitle(java.lang.CharSequence value) {
    this.title = value;
  }

  /**
   * Gets the value of the 'content_bytes' field.
   */
  public java.nio.ByteBuffer getContentBytes() {
    return content_bytes;
  }

  /**
   * Sets the value of the 'content_bytes' field.
   * @param value the value to set.
   */
  public void setContentBytes(java.nio.ByteBuffer value) {
    this.content_bytes = value;
  }

  /**
   * Gets the value of the 'crc32' field.
   */
  public java.lang.CharSequence getCrc32() {
    return crc32;
  }

  /**
   * Sets the value of the 'crc32' field.
   * @param value the value to set.
   */
  public void setCrc32(java.lang.CharSequence value) {
    this.crc32 = value;
  }

  /**
   * Gets the value of the 'md5' field.
   */
  public java.lang.CharSequence getMd5() {
    return md5;
  }

  /**
   * Sets the value of the 'md5' field.
   * @param value the value to set.
   */
  public void setMd5(java.lang.CharSequence value) {
    this.md5 = value;
  }

  /**
   * Gets the value of the 'last_mod_time' field.
   */
  public java.lang.Long getLastModTime() {
    return last_mod_time;
  }

  /**
   * Sets the value of the 'last_mod_time' field.
   * @param value the value to set.
   */
  public void setLastModTime(java.lang.Long value) {
    this.last_mod_time = value;
  }

  /**
   * Gets the value of the 'prev_mod_time' field.
   */
  public java.lang.Long getPrevModTime() {
    return prev_mod_time;
  }

  /**
   * Sets the value of the 'prev_mod_time' field.
   * @param value the value to set.
   */
  public void setPrevModTime(java.lang.Long value) {
    this.prev_mod_time = value;
  }

  /** Creates a new ArticleFile RecordBuilder */
  public static edu.scnu.ArticleFile.Builder newBuilder() {
    return new edu.scnu.ArticleFile.Builder();
  }
  
  /** Creates a new ArticleFile RecordBuilder by copying an existing Builder */
  public static edu.scnu.ArticleFile.Builder newBuilder(edu.scnu.ArticleFile.Builder other) {
    return new edu.scnu.ArticleFile.Builder(other);
  }
  
  /** Creates a new ArticleFile RecordBuilder by copying an existing ArticleFile instance */
  public static edu.scnu.ArticleFile.Builder newBuilder(edu.scnu.ArticleFile other) {
    return new edu.scnu.ArticleFile.Builder(other);
  }
  
  /**
   * RecordBuilder for ArticleFile instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<ArticleFile>
    implements org.apache.avro.data.RecordBuilder<ArticleFile> {

    private java.lang.CharSequence title;
    private java.nio.ByteBuffer content_bytes;
    private java.lang.CharSequence crc32;
    private java.lang.CharSequence md5;
    private long last_mod_time;
    private long prev_mod_time;

    /** Creates a new Builder */
    private Builder() {
      super(edu.scnu.ArticleFile.SCHEMA$);
    }
    
    /** Creates a Builder by copying an existing Builder */
    private Builder(edu.scnu.ArticleFile.Builder other) {
      super(other);
    }
    
    /** Creates a Builder by copying an existing ArticleFile instance */
    private Builder(edu.scnu.ArticleFile other) {
            super(edu.scnu.ArticleFile.SCHEMA$);
      if (isValidValue(fields()[0], other.title)) {
        this.title = data().deepCopy(fields()[0].schema(), other.title);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.content_bytes)) {
        this.content_bytes = data().deepCopy(fields()[1].schema(), other.content_bytes);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.crc32)) {
        this.crc32 = data().deepCopy(fields()[2].schema(), other.crc32);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.md5)) {
        this.md5 = data().deepCopy(fields()[3].schema(), other.md5);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.last_mod_time)) {
        this.last_mod_time = data().deepCopy(fields()[4].schema(), other.last_mod_time);
        fieldSetFlags()[4] = true;
      }
      if (isValidValue(fields()[5], other.prev_mod_time)) {
        this.prev_mod_time = data().deepCopy(fields()[5].schema(), other.prev_mod_time);
        fieldSetFlags()[5] = true;
      }
    }

    /** Gets the value of the 'title' field */
    public java.lang.CharSequence getTitle() {
      return title;
    }
    
    /** Sets the value of the 'title' field */
    public edu.scnu.ArticleFile.Builder setTitle(java.lang.CharSequence value) {
      validate(fields()[0], value);
      this.title = value;
      fieldSetFlags()[0] = true;
      return this; 
    }
    
    /** Checks whether the 'title' field has been set */
    public boolean hasTitle() {
      return fieldSetFlags()[0];
    }
    
    /** Clears the value of the 'title' field */
    public edu.scnu.ArticleFile.Builder clearTitle() {
      title = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /** Gets the value of the 'content_bytes' field */
    public java.nio.ByteBuffer getContentBytes() {
      return content_bytes;
    }
    
    /** Sets the value of the 'content_bytes' field */
    public edu.scnu.ArticleFile.Builder setContentBytes(java.nio.ByteBuffer value) {
      validate(fields()[1], value);
      this.content_bytes = value;
      fieldSetFlags()[1] = true;
      return this; 
    }
    
    /** Checks whether the 'content_bytes' field has been set */
    public boolean hasContentBytes() {
      return fieldSetFlags()[1];
    }
    
    /** Clears the value of the 'content_bytes' field */
    public edu.scnu.ArticleFile.Builder clearContentBytes() {
      content_bytes = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /** Gets the value of the 'crc32' field */
    public java.lang.CharSequence getCrc32() {
      return crc32;
    }
    
    /** Sets the value of the 'crc32' field */
    public edu.scnu.ArticleFile.Builder setCrc32(java.lang.CharSequence value) {
      validate(fields()[2], value);
      this.crc32 = value;
      fieldSetFlags()[2] = true;
      return this; 
    }
    
    /** Checks whether the 'crc32' field has been set */
    public boolean hasCrc32() {
      return fieldSetFlags()[2];
    }
    
    /** Clears the value of the 'crc32' field */
    public edu.scnu.ArticleFile.Builder clearCrc32() {
      crc32 = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    /** Gets the value of the 'md5' field */
    public java.lang.CharSequence getMd5() {
      return md5;
    }
    
    /** Sets the value of the 'md5' field */
    public edu.scnu.ArticleFile.Builder setMd5(java.lang.CharSequence value) {
      validate(fields()[3], value);
      this.md5 = value;
      fieldSetFlags()[3] = true;
      return this; 
    }
    
    /** Checks whether the 'md5' field has been set */
    public boolean hasMd5() {
      return fieldSetFlags()[3];
    }
    
    /** Clears the value of the 'md5' field */
    public edu.scnu.ArticleFile.Builder clearMd5() {
      md5 = null;
      fieldSetFlags()[3] = false;
      return this;
    }

    /** Gets the value of the 'last_mod_time' field */
    public java.lang.Long getLastModTime() {
      return last_mod_time;
    }
    
    /** Sets the value of the 'last_mod_time' field */
    public edu.scnu.ArticleFile.Builder setLastModTime(long value) {
      validate(fields()[4], value);
      this.last_mod_time = value;
      fieldSetFlags()[4] = true;
      return this; 
    }
    
    /** Checks whether the 'last_mod_time' field has been set */
    public boolean hasLastModTime() {
      return fieldSetFlags()[4];
    }
    
    /** Clears the value of the 'last_mod_time' field */
    public edu.scnu.ArticleFile.Builder clearLastModTime() {
      fieldSetFlags()[4] = false;
      return this;
    }

    /** Gets the value of the 'prev_mod_time' field */
    public java.lang.Long getPrevModTime() {
      return prev_mod_time;
    }
    
    /** Sets the value of the 'prev_mod_time' field */
    public edu.scnu.ArticleFile.Builder setPrevModTime(long value) {
      validate(fields()[5], value);
      this.prev_mod_time = value;
      fieldSetFlags()[5] = true;
      return this; 
    }
    
    /** Checks whether the 'prev_mod_time' field has been set */
    public boolean hasPrevModTime() {
      return fieldSetFlags()[5];
    }
    
    /** Clears the value of the 'prev_mod_time' field */
    public edu.scnu.ArticleFile.Builder clearPrevModTime() {
      fieldSetFlags()[5] = false;
      return this;
    }

    @Override
    public ArticleFile build() {
      try {
        ArticleFile record = new ArticleFile();
        record.title = fieldSetFlags()[0] ? this.title : (java.lang.CharSequence) defaultValue(fields()[0]);
        record.content_bytes = fieldSetFlags()[1] ? this.content_bytes : (java.nio.ByteBuffer) defaultValue(fields()[1]);
        record.crc32 = fieldSetFlags()[2] ? this.crc32 : (java.lang.CharSequence) defaultValue(fields()[2]);
        record.md5 = fieldSetFlags()[3] ? this.md5 : (java.lang.CharSequence) defaultValue(fields()[3]);
        record.last_mod_time = fieldSetFlags()[4] ? this.last_mod_time : (java.lang.Long) defaultValue(fields()[4]);
        record.prev_mod_time = fieldSetFlags()[5] ? this.prev_mod_time : (java.lang.Long) defaultValue(fields()[5]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }
}
