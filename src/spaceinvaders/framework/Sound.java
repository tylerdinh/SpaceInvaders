package spaceinvaders.framework;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


/**
 * 	This class gives a simple way of playing sounds or music during the game
 * 	with the ability to pause and resume, stop and reset and even loop the
 * 	sound continuously or a given number of times.
 * 
 * 	NOTE: The file extension for the Sound MUST BE A .wav FILE!
 * 
 * 	NOTE: This is a simple lightweight version. Because of this it is IMPORTANT
 * 			to not have very many Sounds loaded at once or you will QUICKLY use
 * 			up all of your memory. In more advanced games with many more sounds
 * 			and background music fading in and out, we would need a much more
 * 			details Sound object.
 * 	
 */
public class Sound {

	
	/** 
	 * 	Minimum allowed Decibel level for the Sound.
	 */
	private static final double MIN_DECIBELS = -65;
	
	/** 
	 * 	Maximum allowed Decibel level for the Sound. This is set to zero which
	 * 	means that the Sound will have no change to its volume.
	 */
	private static final double MAX_DECIBELS = 0;
	
	
	
	/** Name of the Sound */
	private String name;
	/** File path of the Sound's file */
	private String filepath;
	
	/** Sound Input Stream for the Sound File */
	private AudioInputStream stream;
	/** Clip (Line) for playback */
	private Clip clip;
	
	/** Controls for the gain (volume) of the Sound */
	private FloatControl gainControl;
	/** Controls to mute the Sound */
	private BooleanControl muteControl;
	
	/** Current volume for the sound (from 0 to 1) */
	private double volume = 1;
	/** Minimum volume for the Sound */
	private double minVolume = 0;
	/** Maximum volume for the Sound */
	private double maxVolume = 1;
	
	/** True if this Sound is open */
	private boolean open;
	/** True if this Sound is playing */
	private boolean playing;
	
	
	
	
	/**
	 * 	Creates a new Sound with the given filepath location.
	 */
	public Sound( String filepath )
	{
		this( null, filepath );
	}
	
	/**
	 * 	Creates a new Sound with the given name and filepath location.
	 */
	public Sound( String name, String filepath )
	{
		this.name = name;
		this.filepath = filepath;
	}
	
	
	
//==============================================================================
//							OPEN
//==============================================================================
	
	/**
	 * 	Handles loading and opening the Sound's file data. The Sound MUST be 
	 * 	loaded first before it can be played. NOTE that invoking this method
	 * 	reserves memory space for the Sound until it is unloaded and Sounds
	 * 	should be unloaded if they are not going to be used again soon.
	 */
	public void open() 
	{
		// Already open?
		if ( this.isOpen() ) 
			return;
		
		// Open the file
		File file = null;
		AudioInputStream stream = null;
		Clip clip = null;
		try {
			file = new File( this.filepath );
			stream = AudioSystem.getAudioInputStream( file );
			clip = AudioSystem.getClip();
			clip.open( stream );
		
		} catch ( LineUnavailableException ex ) {
			
			// No line in Mixer available for playback
			this.open = false;
			System.out.println( 
					"\n\t| Audio System failed finding Line for playback: " + this.name + " (" + this.filepath + ") |\n"
				);
			ex.printStackTrace();
			return;
			
		} catch( UnsupportedAudioFileException ex ) {
			
			// File type is not supported
			this.open = false;
			System.out.println( 
					"\n\t| Sound file type extension is not a valid type of file: " + this.name + " (" + this.filepath + ") |\n"
				);
			ex.printStackTrace();
			return;
			
		} catch( IOException ex ) {
			
			// Error with opening the file or filepath
			this.open = false;
			System.out.println( 
					"\n\t| Sound's filepath is not valid or does not exist: " + this.name + " (" + this.filepath + ") |\n"
				);
			ex.printStackTrace();
			return;
		}
		
		// Stream was successfully loaded
		this.stream = stream;
		this.clip = clip;
		this.createControls( clip );
		this.open = true;
	}
	
	/**
	 * 	Closes this Sound and releases any resources (MEMORY) that it was using
	 * 	in the System. This is important to do when you have multiple sounds
	 * 	of large sized sounds otherwise you will run out of local memory.
	 */
	public void close()
	{
		// Not opened?
		if ( !this.isOpen() )
			return;
		
		// Close the Clip
		this.clip.stop();
		this.clip.close();
		this.clip = null;
		this.open = false;
		this.clearControls();
		
		// Close the Stream
		try {
			this.stream.close();
			
		} catch ( IOException ex ) {
			
			// Error with trying to close the stream
			System.out.println( 
					"\n\t| Sound was unable to be closed: " + this.name + " (" + this.filepath + ") |\n"
				);
			ex.printStackTrace();
			return;
		}
		this.stream = null;
	}
	
	/**
	 * 	Returns true if this Sound has is currently loaded and open. A Sound
	 * 	must be opened before it can be played.
	 */
	public boolean isOpen()
	{
		return	this.open;
	}
	
//==============================================================================
//							PLAYBACK
//==============================================================================

	/**
	 * 	Attempts to start this Sound playing from the beginning, but only if
	 * 	the Sound has been opened.
	 */
	public void start()
	{
		// Not open?
		if ( !this.isOpen() )
			return;
		
		// Already playing?
		if ( this.isPlaying() )
			return;
		
		// Update volume
		this.updateGain( this.volume );
		
		// Start the Sound
		this.clip.setMicrosecondPosition( 0 );
		this.clip.start();
		this.playing = true;
	}
	
	/**
	 * 	Attempts to start this Sound playing from the frame position it last
	 * 	played, but only if the Sound has been opened.
	 */
	public void resume()
	{
		// Not open?
		if ( !this.isOpen() )
			return;
		
		// Already playing?
		if ( this.isPlaying() )
			return;

		// Update volume
		this.updateGain( this.volume );
		
		// Start the Sound
		this.clip.start();
		this.playing = true;
	}
	
	/**
	 * 	Resets the Sound's playback to the beginning of the Sound. This method
	 * 	does NOT stop or start the Sound, but merely resets the frame position
	 * 	to zero (or the start).
	 */
	public void reset()
	{
		// Not open?
		if ( !this.isOpen() )
			return;
		
		// Reset the Sound
		this.clip.setMicrosecondPosition( 0 );
	}
	
	/**
	 * 	Stops the Sound's playback and resets it to the beginning position, but
	 * 	only if the Sound is opened.
	 */
	public void stop()
	{
		// Not open?
		if ( !this.isOpen() )
			return;
		
		// Not playing?
		if ( !this.isPlaying() )
			return;
		
		// Stop the Sound
		this.clip.stop();
		this.clip.setMicrosecondPosition( 0 );
		this.playing = false;
	}
	
	/**
	 * 	Pauses the Sound's playback at the current position, but only if the 
	 * 	Sound is opened.
	 */
	public void pause()
	{
		// Not open?
		if ( !this.isOpen() )
			return;
		
		// Not playing?
		if ( !this.isPlaying() )
			return;
		
		// Stop the Sound
		this.clip.stop();
		this.playing = false;
	}
	
	/**
	 * 	Starts the Sound playing on a continuous loop from the beginning, but
	 * 	only if it is opened.
	 */
	public void loop()
	{
		// Not open?
		if ( !this.isOpen() )
			return;
		
		// Already playing?
		if ( this.isPlaying() )
			return;
		
		// Update volume
		this.updateGain( this.volume );
		
		// Start the Sound
		this.clip.setMicrosecondPosition( 0 );
		this.clip.loop( Clip.LOOP_CONTINUOUSLY );
		this.playing = true;
	}
	
	/**
	 * 	Starts the Sound playing from the beginning on loop for the given loop 
	 * 	count. The given count is the total number of times the Sound should 
	 * 	play.
	 */
	public void loop( int count )
	{
		// Not open?
		if ( !this.isOpen() )
			return;
		
		// Already playing?
		if ( this.isPlaying() )
			return;
		
		// Update volume
		this.updateGain( this.volume );
		
		// Start the Sound
		this.clip.setMicrosecondPosition( 0 );
		this.clip.loop( count - 1 );
		this.playing = true;
	}
	
	/**
	 * 	Resumes the playback of the Sound from the frame position it last 
	 * 	played on a continuous loop, but only if it is opened.
	 */
	public void resumeLoop()
	{
		// Not open?
		if ( !this.isOpen() )
			return;
		
		// Already playing?
		if ( this.isPlaying() )
			return;
		
		// Update volume
		this.updateGain( this.volume );
		
		// Start the Sound
		this.clip.loop( Clip.LOOP_CONTINUOUSLY );
		this.playing = true;
	}
	
	/**
	 * 	Resumes the playback of the Sound from the frame position it last 
	 * 	played for the given loop count number of times. The given count is 
	 * 	the total number of times the Sound should play. 
	 */
	public void resumeLoop( int count )
	{
		// Not open?
		if ( !this.isOpen() )
			return;
		
		// Already playing?
		if ( this.isPlaying() )
			return;
		
		// Update volume
		this.updateGain( this.volume );
		
		// Start the Sound
		this.clip.loop( count - 1 );
		this.playing = true;
	}
	
	/**
	 * 	Returns true if the Sound is currently playing.
	 */
	public boolean isPlaying()
	{
		return	this.playing;
	}
	
//==============================================================================
//							CONTROLS
//==============================================================================
	
	/** 
	 * 	Removes all current Controls from the Sound.
	 */
	private void clearControls()
	{
		this.gainControl = null;
		this.muteControl = null;
	}
	
	/**
	 * 	Creates the gain and mute controls for this Sound.
	 */
	private void createControls( Clip clip )
	{
		// GAIN Controls
		if ( clip.isControlSupported( FloatControl.Type.MASTER_GAIN ) )
		{
			this.gainControl = 
				(FloatControl) clip.getControl( FloatControl.Type.MASTER_GAIN );
			
			this.minVolume = this.decibelsToLinear( Math.max(
					this.gainControl.getMinimum(), MIN_DECIBELS
				) );
			this.maxVolume = this.decibelsToLinear( Math.min( 
						this.gainControl.getMaximum(), MAX_DECIBELS 
					) );
		}
		
		// MUTE Controls
		if ( clip.isControlSupported( BooleanControl.Type.MUTE ) )
			this.muteControl = 
				(BooleanControl) clip.getControl( BooleanControl.Type.MUTE );
	}
	
//==============================================================================
//							VOLUME
//==============================================================================
	
	/**
	 * 	Returns the current volume of this Sound. The volume is a normalized
	 * 	value between 0 and 1, where 1 represents 100% full volume and 0
	 * 	represents 0% or completely muted.
	 */
	public double getVolume()
	{
		return	this.volume;
	}
	
	/**
	 * 	Sets the current volume of this Sound. The volume is a normalized
	 * 	value between 0 and 1, where 1 represents 100% full volume and 0
	 * 	represents 0% or completely muted.
	 */
	public void setVolume( double volume )
	{
		// Check standardized bounds
		if ( volume < 0 )	volume = 0;
		if ( volume > 1 )	volume = 1;
		
		// No Change?
		if ( this.volume == volume )
			return;
		
		// Update the volume
		this.volume = volume;
		this.updateGain( volume );
	}
	
	/**
	 * 	Handles setting the correct gain for the Sound based on the given
	 * 	normalized (0 to 1) volume level.
	 */
	private void updateGain( double volume )
	{
		// No gain control?
		if ( this.gainControl == null )
			return;
		
		// Calculate the corresponding dB level
		double range = this.maxVolume - this.minVolume;
		double relVolume = volume * range + this.minVolume;
		double dB = this.linearToDecibels( relVolume );
		
		// Update the volume
		this.gainControl.setValue( (float) dB );
	}
	
	/** Returns the decibel value for the given linear value */
	private double linearToDecibels( double linear )
	{
		double dB = (Math.log( linear ) / Math.log( 10 ) ) * 20;
		return dB;
	}
	
	/** Returns the linear value for the given decibel value */
	private double decibelsToLinear( double dB )
	{
		double linear = Math.pow( 10, dB / 20f );
		return linear;
	}
	
//==============================================================================
//							MUTE
//==============================================================================
	
	/**
	 * 	Returns true if this Sound is muted. If this Sound has not been opened 
	 * 	yet or it does not support mute controls, then this method returns
	 * 	false.
	 */
	public boolean isMuted()
	{
		return	this.muteControl != null	&&
				this.muteControl.getValue();
	}
	
	/**
	 * 	Mutes this Sound if it supports mute controls. If the Sound does
	 * 	not support mute controls or is not opened yet, then this method does
	 * 	nothing.
	 */
	public void mute()
	{
		this.setMuted( true );
	}
	
	/**
	 * 	Un-mutes this Sound if it supports mute controls. If the Sound does
	 * 	not support mute controls or is not opened yet, then this method does
	 * 	nothing.
	 */
	public void unmute()
	{
		this.setMuted( false );
	}
	
	/**
	 * 	Mutes this Sound if true, or unmutes it if false. If this Sound does
	 * 	not support mute controls or is not opened yet, then this method does
	 * 	nothing.
	 */
	public void setMuted( boolean mute )
	{
		// Support mute controls
		if ( this.muteControl != null )
			return;
		
		// Change in state?
		boolean muted = this.muteControl.getValue();
		if ( muted == mute )
			return;
		
		// Update the state
		this.muteControl.setValue( mute );
	}
	
//==============================================================================
//							FILE PATH
//==============================================================================
	
	/**
	 * 	Returns the file path of this Sound's file
	 */
	public String getFilepath() 
	{
		return	this.filepath;
	}
	
//==============================================================================
//							NAME			
//==============================================================================
	
	/**
	 * 	Returns the name of this Sound
	 */
	public String getName() 
	{
		return	this.name;
	}
	
	/**
	 * 	Sets the name of this Sound
	 */
	public void setName( String name ) 
	{
		this.name = name;
	}
	
}