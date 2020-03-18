package com.dts.classes.Mantenimientos.Motivo_devolucion;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.simpleframework.xml.ElementList;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class clsBeMotivo_devolucionList {
    @ElementList(inline=true,required = false)
    public List<clsBeMotivo_devolucion> items = new List<clsBeMotivo_devolucion>() {
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
        public Iterator<clsBeMotivo_devolucion> iterator() {
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
        public boolean add(clsBeMotivo_devolucion clsBeMotivo_devolucion) {
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
        public boolean addAll(@NonNull Collection<? extends clsBeMotivo_devolucion> c) {
            return false;
        }

        @Override
        public boolean addAll(int index, @NonNull Collection<? extends clsBeMotivo_devolucion> c) {
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
        public clsBeMotivo_devolucion get(int index) {
            return null;
        }

        @Override
        public clsBeMotivo_devolucion set(int index, clsBeMotivo_devolucion element) {
            return null;
        }

        @Override
        public void add(int index, clsBeMotivo_devolucion element) {

        }

        @Override
        public clsBeMotivo_devolucion remove(int index) {
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
        public ListIterator<clsBeMotivo_devolucion> listIterator() {
            return null;
        }

        @NonNull
        @Override
        public ListIterator<clsBeMotivo_devolucion> listIterator(int index) {
            return null;
        }

        @NonNull
        @Override
        public List<clsBeMotivo_devolucion> subList(int fromIndex, int toIndex) {
            return null;
        }
    };
}
