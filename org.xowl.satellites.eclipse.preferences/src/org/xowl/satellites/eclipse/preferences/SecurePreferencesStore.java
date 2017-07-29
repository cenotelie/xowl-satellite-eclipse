/*******************************************************************************
 * Copyright (c) 2017 Association Cénotélie (cenotelie.fr)
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General
 * Public License along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package org.xowl.satellites.eclipse.preferences;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.equinox.security.storage.ISecurePreferences;
import org.eclipse.equinox.security.storage.SecurePreferencesFactory;
import org.eclipse.equinox.security.storage.StorageException;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Implements a preference store for secure preferences
 *
 * @author Laurent Wouters
 */
public class SecurePreferencesStore implements IPreferenceStore {
    /**
     * The default suffix
     */
    private static final String DEFAULT = ".__default";

    /**
     * The node for the secure preferences
     */
    private final ISecurePreferences preferences;
    /**
     * The listeners on this store
     */
    private final Collection<IPropertyChangeListener> listeners;

    /**
     * Initializes this store
     *
     * @param path The path to the node in the secured storage
     */
    public SecurePreferencesStore(String path) {
        this.preferences = SecurePreferencesFactory.getDefault().node(path);
        this.listeners = new ArrayList<>();
    }

    @Override
    public void addPropertyChangeListener(IPropertyChangeListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removePropertyChangeListener(IPropertyChangeListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void firePropertyChangeEvent(String name, Object oldValue, Object newValue) {
        PropertyChangeEvent event = new PropertyChangeEvent(this, name, oldValue, newValue);
        for (IPropertyChangeListener listener : listeners) {
            listener.propertyChange(event);
        }
    }

    @Override
    public boolean getDefaultBoolean(String name) {
        try {
            return preferences.getBoolean(name + DEFAULT, IPreferenceStore.BOOLEAN_DEFAULT_DEFAULT);
        } catch (StorageException exception) {
            Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.getDefault().getBundle().getSymbolicName(), exception.getMessage(), exception));
        }
        return false;
    }

    @Override
    public double getDefaultDouble(String name) {
        try {
            return preferences.getDouble(name + DEFAULT, IPreferenceStore.DOUBLE_DEFAULT_DEFAULT);
        } catch (StorageException exception) {
            Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.getDefault().getBundle().getSymbolicName(), exception.getMessage(), exception));
        }
        return IPreferenceStore.DOUBLE_DEFAULT_DEFAULT;
    }

    @Override
    public float getDefaultFloat(String name) {
        try {
            return preferences.getFloat(name + DEFAULT, IPreferenceStore.FLOAT_DEFAULT_DEFAULT);
        } catch (StorageException exception) {
            Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.getDefault().getBundle().getSymbolicName(), exception.getMessage(), exception));
        }
        return IPreferenceStore.FLOAT_DEFAULT_DEFAULT;
    }

    @Override
    public int getDefaultInt(String name) {
        try {
            return preferences.getInt(name + DEFAULT, IPreferenceStore.INT_DEFAULT_DEFAULT);
        } catch (StorageException exception) {
            Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.getDefault().getBundle().getSymbolicName(), exception.getMessage(), exception));
        }
        return IPreferenceStore.INT_DEFAULT_DEFAULT;
    }

    @Override
    public long getDefaultLong(String name) {
        try {
            return preferences.getLong(name + DEFAULT, IPreferenceStore.LONG_DEFAULT_DEFAULT);
        } catch (StorageException exception) {
            Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.getDefault().getBundle().getSymbolicName(), exception.getMessage(), exception));
        }
        return IPreferenceStore.LONG_DEFAULT_DEFAULT;
    }

    @Override
    public String getDefaultString(String name) {
        try {
            return preferences.get(name + DEFAULT, IPreferenceStore.STRING_DEFAULT_DEFAULT);
        } catch (StorageException exception) {
            Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.getDefault().getBundle().getSymbolicName(), exception.getMessage(), exception));
        }
        return IPreferenceStore.STRING_DEFAULT_DEFAULT;
    }

    @Override
    public boolean contains(String name) {
        try {
            return preferences.get(name, null) != null;
        } catch (StorageException exception) {
            Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.getDefault().getBundle().getSymbolicName(), exception.getMessage(), exception));
        }
        return false;
    }

    @Override
    public boolean getBoolean(String name) {
        try {
            return preferences.getBoolean(name, getDefaultBoolean(name));
        } catch (StorageException exception) {
            Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.getDefault().getBundle().getSymbolicName(), exception.getMessage(), exception));
        }
        return false;
    }

    @Override
    public double getDouble(String name) {
        try {
            return preferences.getDouble(name, getDefaultDouble(name));
        } catch (StorageException exception) {
            Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.getDefault().getBundle().getSymbolicName(), exception.getMessage(), exception));
        }
        return IPreferenceStore.DOUBLE_DEFAULT_DEFAULT;
    }

    @Override
    public float getFloat(String name) {
        try {
            return preferences.getFloat(name, getDefaultFloat(name));
        } catch (StorageException exception) {
            Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.getDefault().getBundle().getSymbolicName(), exception.getMessage(), exception));
        }
        return IPreferenceStore.FLOAT_DEFAULT_DEFAULT;
    }

    @Override
    public int getInt(String name) {
        try {
            return preferences.getInt(name, IPreferenceStore.INT_DEFAULT_DEFAULT);
        } catch (StorageException exception) {
            Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.getDefault().getBundle().getSymbolicName(), exception.getMessage(), exception));
        }
        return IPreferenceStore.INT_DEFAULT_DEFAULT;
    }

    @Override
    public long getLong(String name) {
        try {
            return preferences.getLong(name, getDefaultLong(name));
        } catch (StorageException exception) {
            Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.getDefault().getBundle().getSymbolicName(), exception.getMessage(), exception));
        }
        return IPreferenceStore.LONG_DEFAULT_DEFAULT;
    }

    @Override
    public String getString(String name) {
        try {
            return preferences.get(name, getDefaultString(name));
        } catch (StorageException exception) {
            Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.getDefault().getBundle().getSymbolicName(), exception.getMessage(), exception));
        }
        return IPreferenceStore.STRING_DEFAULT_DEFAULT;
    }

    @Override
    public boolean isDefault(String name) {
        try {
            return preferences.get(name, IPreferenceStore.STRING_DEFAULT_DEFAULT) == getDefaultString(name);
        } catch (StorageException exception) {
            Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.getDefault().getBundle().getSymbolicName(), exception.getMessage(), exception));
        }
        return true;
    }

    @Override
    public boolean needsSaving() {
        return false;
    }

    @Override
    public void setDefault(String name, double value) {
        try {
            preferences.putDouble(name + DEFAULT, value, true);
        } catch (StorageException exception) {
            Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.getDefault().getBundle().getSymbolicName(), exception.getMessage(), exception));
        }
    }

    @Override
    public void setDefault(String name, float value) {
        try {
            preferences.putFloat(name + DEFAULT, value, true);
        } catch (StorageException exception) {
            Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.getDefault().getBundle().getSymbolicName(), exception.getMessage(), exception));
        }
    }

    @Override
    public void setDefault(String name, int value) {
        try {
            preferences.putInt(name + DEFAULT, value, true);
        } catch (StorageException exception) {
            Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.getDefault().getBundle().getSymbolicName(), exception.getMessage(), exception));
        }
    }

    @Override
    public void setDefault(String name, long value) {
        try {
            preferences.putLong(name + DEFAULT, value, true);
        } catch (StorageException exception) {
            Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.getDefault().getBundle().getSymbolicName(), exception.getMessage(), exception));
        }
    }

    @Override
    public void setDefault(String name, String value) {
        try {
            preferences.put(name + DEFAULT, value, true);
        } catch (StorageException exception) {
            Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.getDefault().getBundle().getSymbolicName(), exception.getMessage(), exception));
        }
    }

    @Override
    public void setDefault(String name, boolean value) {
        try {
            preferences.putBoolean(name + DEFAULT, value, true);
        } catch (StorageException exception) {
            Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.getDefault().getBundle().getSymbolicName(), exception.getMessage(), exception));
        }
    }

    @Override
    public void setToDefault(String name) {
        setValue(name, getDefaultString(name));
    }

    @Override
    public void putValue(String name, String value) {
        try {
            preferences.put(name, value, true);
        } catch (StorageException exception) {
            Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.getDefault().getBundle().getSymbolicName(), exception.getMessage(), exception));
        }
    }

    @Override
    public void setValue(String name, double value) {
        try {
            preferences.putDouble(name, value, true);
        } catch (StorageException exception) {
            Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.getDefault().getBundle().getSymbolicName(), exception.getMessage(), exception));
        }
    }

    @Override
    public void setValue(String name, float value) {
        try {
            preferences.putFloat(name, value, true);
        } catch (StorageException exception) {
            Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.getDefault().getBundle().getSymbolicName(), exception.getMessage(), exception));
        }
    }

    @Override
    public void setValue(String name, int value) {
        try {
            preferences.putInt(name, value, true);
        } catch (StorageException exception) {
            Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.getDefault().getBundle().getSymbolicName(), exception.getMessage(), exception));
        }
    }

    @Override
    public void setValue(String name, long value) {
        try {
            preferences.putLong(name, value, true);
        } catch (StorageException exception) {
            Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.getDefault().getBundle().getSymbolicName(), exception.getMessage(), exception));
        }
    }

    @Override
    public void setValue(String name, String value) {
        try {
            preferences.put(name, value, true);
        } catch (StorageException exception) {
            Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.getDefault().getBundle().getSymbolicName(), exception.getMessage(), exception));
        }
    }

    @Override
    public void setValue(String name, boolean value) {
        try {
            preferences.putBoolean(name, value, true);
        } catch (StorageException exception) {
            Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.getDefault().getBundle().getSymbolicName(), exception.getMessage(), exception));
        }
    }
}
