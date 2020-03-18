package com.dts.classes.Transacciones.CambioUbicacion.clsBeMotivo_ubicacion;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dts.classes.Transacciones.CambioUbicacion.clsBeMotivo_ubicacion.clsBeMotivo_ubicacion;

import org.simpleframework.xml.ElementList;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class clsBeMotivo_ubicacionList {
    @ElementList(inline=true,required = false)
    public List<clsBeMotivo_ubicacion> items = new List<clsBeMotivo_ubicacion>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(@Nullable Object o) {
            return false;
        }

        @NonNull
        @Override
        public Iterator<clsBeMotivo_ubicacion> iterator() {
            return null;
        }

        @Nullable
        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @Override
        public <T> T[] toArray(@Nullable T[] a) {
            return null;
        }

        @Override
        public boolean add(clsBeMotivo_ubicacion clsBeMotivo_ubicacion) {
            return false;
        }

        @Override
        public boolean remove(@Nullable Object o) {
            return false;
        }

        @Override
        public boolean containsAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public boolean addAll(@NonNull Collection<? extends clsBeMotivo_ubicacion> c) {
            return false;
        }

        @Override
        public boolean addAll(int index, @NonNull Collection<? extends clsBeMotivo_ubicacion> c) {
            return false;
        }

        @Override
        public boolean removeAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public boolean retainAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public boolean equals(@Nullable Object o) {
            return false;
        }

        @Override
        public int hashCode() {
            return 0;
        }

        @Override
        public clsBeMotivo_ubicacion get(int index) {
            return null;
        }

        @Override
        public clsBeMotivo_ubicacion set(int index, clsBeMotivo_ubicacion element) {
            return null;
        }

        @Override
        public void add(int index, clsBeMotivo_ubicacion element) {

        }

        @Override
        public clsBeMotivo_ubicacion remove(int index) {
            return null;
        }

        @Override
        public int indexOf(@Nullable Object o) {
            return 0;
        }

        @Override
        public int lastIndexOf(@Nullable Object o) {
            return 0;
        }

        @NonNull
        @Override
        public ListIterator<clsBeMotivo_ubicacion> listIterator() {
            return null;
        }

        @NonNull
        @Override
        public ListIterator<clsBeMotivo_ubicacion> listIterator(int index) {
            return null;
        }

        @NonNull
        @Override
        public List<clsBeMotivo_ubicacion> subList(int fromIndex, int toIndex) {
            return null;
        }
    };
}


//--------------------------------------------------------
